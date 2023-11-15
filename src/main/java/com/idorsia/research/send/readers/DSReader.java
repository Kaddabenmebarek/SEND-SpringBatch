package com.idorsia.research.send.readers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.Disposition;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssignmentDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("dsReader")
@Scope("step")
public class DSReader implements ItemReader<GenericDomain<Disposition>> {

	private String ctversion;
	private StudyDto study;
	private BiosampleService biosampleService;
	private StudyService studyService;
	private AssignmentService assignmentService;
	private StepExecution stepExecution;
	private static final String DSDONE = "dsdone";

	@Override
	public GenericDomain<Disposition> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(DSDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(DSDONE)) {
			return null;
		}
		System.out.println("Calling DS READER");
		List<Disposition> dsList = getDisposistions();
		stepExecution.getJobExecution().getExecutionContext().put(DSDONE, true);
		return new GenericDomain<Disposition>(dsList);
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.stepExecution = stepExecution;
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
	}

	private List<Disposition> getDisposistions() {
		List<BiosampleDto> biosamples = getStudyService().getBiosamples(study);
		List<String> dsDecodList = SendService.getTerminologyByCode(ctversion, "DSDECOD");
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT4, Locale.ENGLISH);
		List<Disposition> dsList = new ArrayList<Disposition>();
		Map<Date, Duration> datePhaseRefMap = new HashMap<Date, Duration>();
		for (BiosampleDto bs : biosamples) {
			if (bs.getEndDate() == null)
				continue;
			Disposition ds = new Disposition();
			ds.setStudyId(study.getStudyId());
			ds.setDomain(Constants.DOMAIN_DS);
			ds.setUsubjId(bs.getSampleId());
			List<AssignmentDto> assignments = getBiosampleService().getAssignments(bs, study);
			for (AssignmentDto asn : assignments) {
				if (getAssignmentService().getRemovalDate(asn) != null) {
					ds.setDsStdtc(DataUtils.toTimestamp(getAssignmentService().getRemovalDate(asn)));
				} else {
					ds.setDsStdtc(DataUtils.toTimestamp(bs.getTerminationExecution().getExecutionDate()));
				}
			}
			ds.setsDsStdtc(sdf.format(ds.getDsStdtc()));
			long dy;
			if (bs.getEndPhase() == null || bs.getEndPhase().getPhase() == null) {
				dy = DataUtils.getPhase(bs.getTerminationExecution().getExecutionDate(), datePhaseRefMap);
			} else {
				dy = bs.getEndPhase().getPhase().toDays();
				if (datePhaseRefMap.size() == 0) {
					datePhaseRefMap.put(bs.getTerminationExecution().getExecutionDate(), bs.getEndPhase().getPhase());
				}
			}
			ds.setDsStdy(dy);
			ds.setDsNomdy(String.valueOf(dy));
			getDisposalCauses(ds, bs, dsDecodList);
			dsList.add(ds);
		}
		Collections.sort(dsList, new Comparator<Disposition>() {
			@Override
			public int compare(Disposition d1, Disposition d2) {
				return d1.getUsubjId().compareTo(d2.getUsubjId());
			}
		});
		return dsList;
	}

	private void getDisposalCauses(Disposition ds, BiosampleDto bioSample, List<String> dsDecodList) {
		String dsDecodToUse;
		String dsTermToUse;
		String uSchDlFlah;
		String biosampleState = bioSample.getState() != null ? bioSample.getState().getName() : "";
		switch (biosampleState) {
		case "Necropsied":
			dsDecodToUse = DataUtils.getTerminologyCandidate("terminal", dsDecodList);
			dsTermToUse = "Final Phase sacrifice";
			uSchDlFlah = "N";
			break;
		case "Found Dead":
			dsDecodToUse = DataUtils.getTerminologyCandidate("found dead", dsDecodList);
			dsTermToUse = "Found dead";
			uSchDlFlah = "Y";
			break;
		case "Killed":
			dsDecodToUse = DataUtils.getTerminologyCandidate("moribund sacrifice", dsDecodList);
			dsTermToUse = "Moribund killed";
			uSchDlFlah = "Y";
			break;
		default:
			dsDecodToUse = "";
			dsTermToUse = "";
			uSchDlFlah = "N";
			break;
		}
		ds.setDsDecod(dsDecodToUse);
		ds.setDsTerm(dsTermToUse);
		ds.setDsUschFl(uSchDlFlah);
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public BiosampleService getBiosampleService() {
		return biosampleService == null ? ContextServices.getBiosampleService() : biosampleService;
	}

	public AssignmentService getAssignmentService() {
		return assignmentService == null ? ContextServices.getAssignmentService() : assignmentService;
	}

}
