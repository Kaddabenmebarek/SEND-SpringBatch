package com.idorsia.research.send.readers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

import com.actelion.research.business.ppg.formulation.PpgFormulationCompound;
import com.actelion.research.business.ppg.formulation.PpgTreatment;
import com.actelion.research.business.spi.formulation.SPIFormuCompound;
import com.actelion.research.business.spi.formulation.SpiFormulation;
import com.idorsia.research.send.ContextServices;
import com.idorsia.research.send.domain.Exposure;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.ActionPatternsDto;
import com.idorsia.research.spirit.core.dto.AdministrationDto;
import com.idorsia.research.spirit.core.dto.AssignmentDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.NamedTreatmentDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.model.NamedTreatment;
import com.idorsia.research.spirit.core.service.AdministrationService;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.NamedTreatmentService;
import com.idorsia.research.spirit.core.service.PhaseService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("exReader")
@Scope("step")
public class EXReader implements ItemReader<GenericDomain<Exposure>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private String ctversion;
	private Set<String> keys = new HashSet<String>();
	private static final String EXDONE = "exdone";
	private StudyService studyService;
	private BiosampleService biosampleService;
	private AdministrationService administrationService;
	private AssignmentService assignmentService;
	private NamedTreatmentService namedTreatmentService;
	private PhaseService phaseService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
		DataUtils.clearContext(stepExecution, keys);
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
	}

	@Override
	public GenericDomain<Exposure> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(EXDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(EXDONE)) {
			return null;
		}
		System.out.println("Calling EX READER");
		List<Exposure> exList = getExosuresFromSpiritCore();
		stepExecution.getJobExecution().getExecutionContext().put(EXDONE, true);
		return new GenericDomain<Exposure>(exList);
	}

	private List<Exposure> getExosuresFromSpiritCore() {
		List<Exposure> items = new ArrayList<>();
		Set<Record> records = new HashSet<Record>();
		Map<String, Map<Date, Duration>> sampleDurationDateMap = new HashMap<String, Map<Date, Duration>>();
		List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
		for (BiosampleDto biosample : biosamples) {
			List<AdministrationDto> administrations = getAdministrationService()
					.map(getAdministrationService().getByBiosample(biosample.getId()));
			for (AdministrationDto a : administrations) {
				AssignmentDto asn = getBiosampleService().getAssignment(biosample, a.getPhase().getStage());
				if (asn != null) {
					Date recordDate = getAssignmentService().getDate(asn, a.getPhase());
					if (sampleDurationDateMap.get(biosample.getSampleId()) == null) {
						Map<Date, Duration> durationDateMap = new HashMap<Date, Duration>();
						durationDateMap.put(recordDate, a.getPhase().getPhase());
						sampleDurationDateMap.put(biosample.getSampleId(), durationDateMap);
					}
					NamedTreatmentDto trt = a.getNamedTreatment();
					String rRule = null;
					List<ActionPatternsDto> apList = getPhaseService().getActionpatterns(a.getPhase().getStage(), trt);
					for (ActionPatternsDto ap : apList) {
						if (ap.getrRule() != null)
							rRule = ap.getrRule();
						break;
					}
					NamedTreatment trtPojo = getNamedTreatmentService().get(trt.getId());
					PpgTreatment ppg = getPpgTreatment(trtPojo.getPpgTreatmentInstanceId());
					SpiFormulation spi = getSpiTreatment(trtPojo.getSpiTreatmentId());
					records.add(new Record(recordDate, biosample, a, trt, ppg, spi, a.getPhase().getPhase(), rRule));
				}
			}
			List<String> freqList = DataUtils.getCT(stepExecution, ctversion, "FREQ");
			for (Record rec : records) {
				Exposure exp = new Exposure();
				exp.setStudyId(study.getStudyId());
				exp.setDomain("EX");
				// exp.setExStdtc(rec.getDate());
				SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT4, Locale.ENGLISH);
				String sExStdtc = sdf.format(rec.getDate());
				exp.setsExStdtc(sExStdtc);
				exp.setSubjId(rec.getBiosample().getSampleId());
				exp.setUsubjId(exp.getSubjId());
				exp.setExStDy(rec.getPhase().toDays());

				String rRule = rec.getrRule() == null ? "" : rec.getrRule();
				String freq = rRule.isBlank() ? "" : rRule.split(";")[0].split("=")[1];
				switch (freq) {
				case "WEEKLY":
					//exp.setExDosFrq(DataUtils.getTerminologyCandidate("1 per week", freqList));
					Integer count = null;
					if(rRule.split(";")[2].contains("BYDAY")) {
						count = rRule.split(";")[0].split(",").length;
					}
					exp.setExDosFrq(count + " TIMES PER YEAR");
					break;
				case "":
					exp.setExDosFrq("ONCE");
					break;
				default:
					exp.setExDosFrq(DataUtils.getTerminologyCandidate("qd", freqList));
					break;
				}

				if (rec.getPpgTreatment() != null) {
					PpgTreatment ppgTrt = rec.getPpgTreatment();
					if (ppgTrt.getCompounds() != null && !ppgTrt.getCompounds().isEmpty()) {
						PpgFormulationCompound ppgfc = ppgTrt.getCompounds().get(0);
						exp.setExTrt(ppgfc.getActNumber());
						exp.setExDose(ppgfc.getDose());
						String doseUnit = ppgfc.getDoseUnit().getSymbol() != null ? ppgfc.getDoseUnit().getSymbol()
								: "";
						exp.setExDosU(doseUnit);
					} else {
						exp.setExTrt(rec.getNamedTreatment().getName());
					}
					String vehicleName = ppgTrt.getVehicle() != null ? ppgTrt.getVehicle().getName() : "";
					exp.setExTrtv(vehicleName);
					exp.setExVamt(String.valueOf(ppgTrt.getVolume()));
					exp.setExVamtu(ppgTrt.getVolumeUnit().getSymbol());
					getDoseForm(exp, ppgTrt, null, false);
					getRoute(exp, ppgTrt, null);
					exp.setPpgId(ppgTrt.getTreatmentInstanceId());
				} else if (rec.getSpiFormulation() != null) {
					SpiFormulation spiTrt = rec.getSpiFormulation();
					if (spiTrt.getFormulation_compounds() != null && !spiTrt.getFormulation_compounds().isEmpty()) {
						SPIFormuCompound spifc = spiTrt.getFormulation_compounds().get(0);
						exp.setExTrt(spifc.getActive_cmpd().getName());
						exp.setExDose(spifc.getDose_unit().getRatio());
						String doseUnit = spifc.getDose_unit().getSymbols() != null ? spifc.getDose_unit().getSymbols()
								: "";
						exp.setExDosU(doseUnit);
					} else {
						exp.setExTrt(rec.getNamedTreatment().getName());
					}
					String spiVehicle = spiTrt.getFormulation_vehicles() != null
							&& !spiTrt.getFormulation_vehicles().isEmpty()
									? spiTrt.getFormulation_vehicles().get(0).getVehicle().getName()
									: "";
					exp.setExTrtv(spiVehicle);
					exp.setExVamt(String.valueOf(spiTrt.getTotal_formulation_amount()));
					exp.setExVamtu(spiTrt.getTotal_formulation_unit().getSymbols());
					getDoseForm(exp, null, spiTrt, false);
					getRoute(exp, null, spiTrt);
					exp.setSpiId(spiTrt.getId());
				} else {// disease
					exp.setExTrt(rec.getNamedTreatment().getName());
					exp.setExDosFrm(ctversion);
					getDoseForm(exp, null, null, true);
				}
				items.add(exp);
			}
		}
		stepExecution.getJobExecution().getExecutionContext().put("sampleDurationDateMap", sampleDurationDateMap);
		keys.add("FRM");
		keys.add("ROUTE");
		keys.add("FREQ");
		return items;
	}

	private SpiFormulation getSpiTreatment(Integer spiId) {
		if (spiId == null) return null;
		SpiFormulation spi;
		String key = "spi" + Constants.UNDERSCORE + spiId;
		if (stepExecution.getJobExecution().getExecutionContext().get(key) != null) {
			spi = (SpiFormulation) stepExecution.getJobExecution().getExecutionContext().get(key);
		} else {
			spi = getNamedTreatmentService().getSpiFormulation(spiId);
			stepExecution.getJobExecution().getExecutionContext().put(key, spi);
			keys.add(key);
		}
		return spi;
	}

	private PpgTreatment getPpgTreatment(Integer ppgId) {
		if (ppgId == null) return null;
		PpgTreatment ppg;
		String key = "ppg" + Constants.UNDERSCORE + ppgId;
		if (stepExecution.getJobExecution().getExecutionContext().get(key) != null) {
			ppg = (PpgTreatment) stepExecution.getJobExecution().getExecutionContext().get(key);
		} else {
			ppg = getNamedTreatmentService().getTreatment(ppgId);
			stepExecution.getJobExecution().getExecutionContext().put(key, ppg);
			keys.add(key);
		}
		return ppg;
	}

	private void getRoute(Exposure item, PpgTreatment ppgTrt, SpiFormulation spiTrt) {
		List<String> routeList = DataUtils.getCT(stepExecution, ctversion, "ROUTE");
		String route;
		if (spiTrt == null) {
			route = ppgTrt.getRoute().getName();
		} else {
			route = spiTrt.getRoute().getName();
		}
		item.setExRoute(DataUtils.getTerminologyCandidate(route, routeList));
	}

	private void getDoseForm(Exposure item, PpgTreatment ppgTrt, SpiFormulation spiTrt, boolean isDisease) {
		String frmCat = "Standard";
		if (!isDisease) {
			if (spiTrt == null) {
				frmCat = ppgTrt.getFormulationTypeCategory() != null ? ppgTrt.getFormulationTypeCategory().getName()
						: "";
			} else {
				frmCat = spiTrt.getFormulation_type().getName() != null ? spiTrt.getFormulation_type().getName() : "";
			}
		}
		List<String> dosFrmList = DataUtils.getCT(stepExecution, ctversion, "FRM");
		String exDosFrm = DataUtils.getTerminologyCandidate(frmCat, dosFrmList);
		item.setExDosFrm(exDosFrm);
	}

	public AdministrationService getAdministrationService() {
		return administrationService == null ? (AdministrationService) ContextServices.getAdministrationService()
				: administrationService;
	}

	public BiosampleService getBiosampleService() {
		return biosampleService == null ? (BiosampleService) ContextServices.getBiosampleService() : biosampleService;
	}

	public AssignmentService getAssignmentService() {
		return assignmentService == null ? (AssignmentService) ContextServices.getAssignmentService()
				: assignmentService;
	}

	public StudyService getStudyService() {
		return studyService == null ? (StudyService) ContextServices.getStudyService() : studyService;
	}

	public NamedTreatmentService getNamedTreatmentService() {
		return namedTreatmentService == null ? (NamedTreatmentService) ContextServices.getNamedTreatmentService()
				: namedTreatmentService;
	}

	public PhaseService getPhaseService() {
		return phaseService == null ? (PhaseService) ContextServices.getPhaseService() : phaseService;
	}

	private class Record {
		private Date date;
		private BiosampleDto biosample;
		private NamedTreatmentDto namedTreatment;
		private PpgTreatment ppgTreatment;
		private SpiFormulation spiFormulation;
		private Duration phase;
		private String rRule;

		public Record(Date date, BiosampleDto biosample, AdministrationDto administration,
				NamedTreatmentDto namedTreatment, PpgTreatment ppgTreatment, SpiFormulation spiFormulation,
				Duration phase, String rRule) {
			this.date = date;
			this.biosample = biosample;
			this.namedTreatment = namedTreatment;
			this.ppgTreatment = ppgTreatment;
			this.spiFormulation = spiFormulation;
			this.phase = phase;
			this.rRule = rRule;
		}

		public Date getDate() {
			return date;
		}

		public BiosampleDto getBiosample() {
			return biosample;
		}

		public NamedTreatmentDto getNamedTreatment() {
			return namedTreatment;
		}

		public PpgTreatment getPpgTreatment() {
			return ppgTreatment;
		}

		public SpiFormulation getSpiFormulation() {
			return spiFormulation;
		}

		public Duration getPhase() {
			return phase;
		}

		public String getrRule() {
			return rRule;
		}

	}

}
