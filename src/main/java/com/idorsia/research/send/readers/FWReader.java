package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.ContextServices;
import com.idorsia.research.send.domain.FoodWater;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.EnclosureDto;
import com.idorsia.research.spirit.core.dto.FoodWaterDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleEnclosureService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.EnclosureService;
import com.idorsia.research.spirit.core.service.FoodWaterService;
import com.idorsia.research.spirit.core.service.PhaseService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("fwReader")
@Scope("step")
public class FWReader implements ItemReader<GenericDomain<FoodWater>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
	private static final String FWDONE = "fwdone";

	private EnclosureService enclosureService;
	private StudyService studyService;
	private FoodWaterService foodWaterService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private BiosampleEnclosureService biosampleEnclosureService;
	private PhaseService phaseService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
	}

	@Override
	public GenericDomain<FoodWater> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(FWDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(FWDONE)) {
			return null;
		}
		System.out.println("Calling FW READER");

		List<FoodWater> foodList = new LinkedList<FoodWater>();
		List<FoodWater> waterList = new LinkedList<FoodWater>();
		List<FoodWater> fwList = new LinkedList<FoodWater>();
		processFoodWater(foodList, waterList);
		fwList.addAll(foodList);
		fwList.addAll(waterList);
		fwList.sort(Comparator.comparing(FoodWater::getUsubjId).thenComparing(FoodWater::getFwDtc));
		Map<String, String> biosamplePoolMap = getBiosamplePoolMap(fwList);
		stepExecution.getJobExecution().getExecutionContext().put("biosamplePoolMap", biosamplePoolMap);
		stepExecution.getJobExecution().getExecutionContext().put(FWDONE, true);
		return new GenericDomain<FoodWater>(fwList);
	}

	private void processFoodWater(List<FoodWater> foodList, List<FoodWater> waterList) throws Exception {
		getAssayResultService().attachOrCreateStudyResultsToTops(study, getStudyService().getSubjectsSorted(study),
				null, null);
		List<FoodWaterDto> fws = getFoodWaterService().map(getFoodWaterService().getByStudy(study.getId()));
		Map<Date, Set<BiosampleDto>> dates = getFoodWaterService().getDatesMap(fws);
		List<BiosampleDto> biosamples = getStudyService().getBiosamples(study);
		for (BiosampleDto b : biosamples) {
			b.getSampleId();
			getBiosampleService().getLastAssignment(b, study).getName();
			getBiosampleService().getEnclosures(b, study).get(0).getName();
			ArrayList<Date> dateSorted = new ArrayList<Date>(dates.keySet());
			Collections.sort(dateSorted);
			for (Date date : dateSorted) {
				PhaseDto phase = getBiosampleService().getPhase(b, study, date, null);
				EnclosureDto enclosure = date == null ? null : getBiosampleService().getEnclosure(b, date);
				if (enclosure != null) {
					FoodWaterDto fw = getFoodWaterService().extract(fws, enclosure.getId(), date);
					FoodWaterDto previousFood = fw == null ? null
							: getFoodWaterService().getPreviousFromList(fw, fws, false);
					addFoodRow(foodList, b, date, phase, fw, previousFood);
					FoodWaterDto previousWater = fw == null ? null
							: getFoodWaterService().getPreviousFromList(fw, fws, true);
					addWaterRow(waterList, b, date, phase, fw, previousWater);
				}
			}
		}
	}

	private void addWaterRow(List<FoodWater> waterList, BiosampleDto b, Date date, PhaseDto phase, FoodWaterDto fw,
			FoodWaterDto previousWater) {
		if (previousWater != null) {
			Date previousDate = previousWater.getFwDate();
			PhaseDto previousPhase = getBiosampleService().getPhase(b, study, previousDate, null);
			BigDecimal animaDay = getEnclosureService().getOccupancyForPeriod(fw.getEnclosure(),
					previousWater.getFwDate(), fw.getFwDate());
			double cons = (previousWater.getWaterTare() - fw.getWaterTare()) / animaDay.doubleValue();
			BigDecimal consToDisplay = new BigDecimal(cons).setScale(1, RoundingMode.HALF_UP);
			FoodWater foodWater = new FoodWater();
			foodWater.setStudyId(study.getStudyId());
			foodWater.setDomain(Constants.DOMAIN_FW);
			foodWater.setUsubjId(b.getSampleId());
			foodWater.setFwTestCd(Constants.FWTESTCD2);
			foodWater.setFwTest(Constants.FWTEST2);
			foodWater.setFwOrres(consToDisplay);
			foodWater.setFwStresc(consToDisplay);
			foodWater.setFwStresn(consToDisplay);
			foodWater.setFwOrresu(Constants.MLUNIT);
			foodWater.setFwStresu("mL/animal/day");
			foodWater.setFwDy(previousPhase.getPhase().toDays());
			foodWater.setFwEndy(phase.getPhase().toDays());
			foodWater.setFwDtc(DataUtils.toTimestamp(previousDate));
			foodWater.setFwEndtc(DataUtils.toTimestamp(date));
			foodWater.setsFwDtc(sdf.format(previousDate));
			foodWater.setsFwEndtc(sdf.format(date));
			EnclosureDto enclosure = getBiosampleService().getEnclosure(b, previousPhase);
			foodWater.setPoolId(enclosure != null ? enclosure.getName() : "");

			waterList.add(foodWater);
		}
	}

	private void addFoodRow(List<FoodWater> foodList, BiosampleDto b, Date date, PhaseDto phase, FoodWaterDto fw,
			FoodWaterDto previousFood) {
		if (previousFood != null) {
			Date previousDate = previousFood.getFwDate();
			PhaseDto previousPhase = getBiosampleService().getPhase(b, study, previousDate, null);
			BigDecimal animaDay = getEnclosureService().getOccupancyForPeriod(fw.getEnclosure(),
					previousFood.getFwDate(), fw.getFwDate());
			double cons = (previousFood.getFoodTare() - fw.getFoodWeight()) / animaDay.doubleValue();
			BigDecimal consToDisplay = new BigDecimal(cons).setScale(1, RoundingMode.HALF_UP);
			FoodWater foodWater = new FoodWater();
			foodWater.setStudyId(study.getStudyId());
			foodWater.setDomain(Constants.DOMAIN_FW);
			foodWater.setUsubjId(b.getSampleId());
			foodWater.setFwTestCd(Constants.FWTESTCD);
			foodWater.setFwTest(Constants.FWTEST);
			foodWater.setFwOrres(consToDisplay);
			foodWater.setFwStresc(consToDisplay);
			foodWater.setFwStresn(consToDisplay);
			foodWater.setFwOrresu(Constants.BWUNIT);
			foodWater.setFwStresu("g/animal/day");
			foodWater.setFwDy(previousPhase.getPhase().toDays());
			foodWater.setFwEndy(phase.getPhase().toDays());
			foodWater.setFwDtc(DataUtils.toTimestamp(previousDate));
			foodWater.setFwEndtc(DataUtils.toTimestamp(date));
			foodWater.setsFwDtc(sdf.format(previousDate));
			foodWater.setsFwEndtc(sdf.format(date));
			EnclosureDto enclosure = getBiosampleService().getEnclosure(b, previousPhase);
			foodWater.setPoolId(enclosure != null ? enclosure.getName() : "");

			foodList.add(foodWater);
		}
	}
	

	private Map<String, String> getBiosamplePoolMap(List<FoodWater> fwList) {
		Map<String, String> res = new HashMap<String, String>();
		for(FoodWater fw : fwList) {
			res.put(fw.getUsubjId(), fw.getPoolId());
		}
		return res;
	}

	public EnclosureService getEnclosureService() {
		return enclosureService == null ? ContextServices.getEnclosureService() : enclosureService;
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public FoodWaterService getFoodWaterService() {
		return foodWaterService == null ? ContextServices.getFoodWaterService() : foodWaterService;
	}

	public BiosampleService getBiosampleService() {
		return biosampleService == null ? ContextServices.getBiosampleService() : biosampleService;
	}

	public BiosampleEnclosureService getBiosampleEnclosureService() {
		return biosampleEnclosureService == null ? ContextServices.getBiosampleEnclosureService()
				: biosampleEnclosureService;
	}

	public AssayResultService getAssayResultService() {
		return assayResultService == null ? ContextServices.getAssayResultService() : assayResultService;
	}

	public PhaseService getPhaseService() {
		return phaseService == null ? ContextServices.getPhaseService() : phaseService;
	}

}
