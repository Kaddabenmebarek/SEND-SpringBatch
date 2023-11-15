package com.idorsia.research.send.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.Exposure;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("exProcessor")
@Scope("step")
public class EXProcessor implements ItemProcessor<GenericDomain<Exposure>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder exSeqLine = new StringBuilder();
	private StringBuilder exTrtLine = new StringBuilder();
	private StringBuilder exDoseLine = new StringBuilder();
	private StringBuilder exDosuLine = new StringBuilder();
	private StringBuilder exDoRfrmLine = new StringBuilder();
	private StringBuilder exDoRfqLine = new StringBuilder();
	private StringBuilder exRouteLine = new StringBuilder();
	private StringBuilder exLotLine = new StringBuilder();
	private StringBuilder exTrtVLine = new StringBuilder();
	private StringBuilder exVamtLine = new StringBuilder();
	private StringBuilder exVamtULine = new StringBuilder();
	private StringBuilder exStdcLine = new StringBuilder();
	private StringBuilder exSdyLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<Exposure> item) throws Exception {
		System.out.println("Calling EX PROCESSOR");
		StringBuilder rsb = generateEXScript("ex.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("ex", rsb.toString());
		return res;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		try {
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			outputPath = rb.getString("output_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder generateEXScript(String inputfileName, List<Exposure> data)
			throws FileNotFoundException, IOException {
		feedLines(data);
		File sourceFile = ResourceUtils.getFile(
				outputPath + File.separator + "templates" + File.separator + "R" + File.separator + inputfileName);
		StringBuilder sb = new StringBuilder();
		FileReader fr = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();

		try {
			while (s != null) {
				if (s.contains("$STUDYID")) {
					s = StringUtils.replace(s, "$STUDYID", studyIdLine.toString());
				}
				if (s.contains("$DOMAIN")) {
					s = StringUtils.replace(s, "$DOMAIN", domainLine.toString());
				}
				if (s.contains("$USUBJID")) {
					s = StringUtils.replace(s, "$USUBJID", usubsIdLine.toString());
				}

				if (s.contains("$EXSEQ")) {
					s = StringUtils.replace(s, "$EXSEQ", exSeqLine.toString());
				}
				if (s.contains("$EXTRT")) {
					s = StringUtils.replace(s, "$EXTRT", exTrtLine.toString());
				}
				if (s.contains("$EXDOSE")) {
					s = StringUtils.replace(s, "$EXDOSE", exDoseLine.toString());
				}
				if (s.contains("$UEXDOS")) {
					s = StringUtils.replace(s, "$UEXDOS", exDosuLine.toString());
				}
				if (s.contains("$EXDORFRM")) {
					s = StringUtils.replace(s, "$EXDORFRM", exDoRfrmLine.toString());
				}
				if (s.contains("$EXDOSFRQ")) {
					s = StringUtils.replace(s, "$EXDOSFRQ", exDoRfqLine.toString());
				}
				if (s.contains("$EXROUTE")) {
					s = StringUtils.replace(s, "$EXROUTE", exRouteLine.toString());
				}
				if (s.contains("$EXLOT")) {
					s = StringUtils.replace(s, "$EXLOT", exLotLine.toString());
				}
				if (s.contains("$VEXTRT")) {
					s = StringUtils.replace(s, "$VEXTRT", exTrtVLine.toString());
				}
				if (s.contains("$EXVAMT")) {
					s = StringUtils.replace(s, "$EXVAMT", exVamtLine.toString());
				}
				if (s.contains("$UEXVAMT")) {
					s = StringUtils.replace(s, "$UEXVAMT", exVamtULine.toString());
				}
				if (s.contains("$EXSTDTC")) {
					s = StringUtils.replace(s, "$EXSTDTC", exStdcLine.toString());
				}
				if (s.contains("$EXSDY")) {
					s = StringUtils.replace(s, "$EXSDY", exSdyLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/ex.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "fw.xpt";
					s = StringUtils.replace(s, "$XPT-OUTPUT-PATH", xptOutputPath);
				}
				sb.append(s);
				sb.append("\n");
				s = br.readLine();
			}
		} finally {
			br.close();
		}
		return sb;
	}

	private void feedLines(List<Exposure> data) {
		Set<Exposure> exSet = removeDuplicates(data);
		List<Exposure> exList = new ArrayList<Exposure>(exSet);
		exList.sort(Comparator.comparing(Exposure::getUsubjId).thenComparing(Exposure::getExStDy));
		for (int i = 0; i < exList.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(exList.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(exList.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(exList.get(i).getUsubjId()).append(Constants.QUOTE);
			exSeqLine.append(i + 1);
			exTrtLine.append(Constants.QUOTE).append(exList.get(i).getExTrt()).append(Constants.QUOTE);
			Double dose = exList.get(i).getExDose() != null ? exList.get(i).getExDose() : 0;
			exDoseLine.append(dose);
			String doseUnit = exList.get(i).getExDosU() != null ? exList.get(i).getExDosU() : "mg/kg";
			exDosuLine.append(Constants.QUOTE).append(doseUnit).append(Constants.QUOTE);
			exDoRfrmLine.append(Constants.QUOTE)
					.append(exList.get(i).getExDosFrm() != null ? exList.get(i).getExDosFrm() : "")
					.append(Constants.QUOTE);
			exDoRfqLine.append(Constants.QUOTE).append(exList.get(i).getExDosFrq()).append(Constants.QUOTE);
			exRouteLine.append(Constants.QUOTE)
					.append(exList.get(i).getExRoute() != null ? exList.get(i).getExRoute() : "")
					.append(Constants.QUOTE);
			exLotLine.append(Constants.QUOTE).append(exList.get(i).getExLot() != null ? exList.get(i).getExLot() : "")
					.append(Constants.QUOTE);
			exTrtVLine.append(Constants.QUOTE)
					.append(exList.get(i).getExTrtv() != null ? exList.get(i).getExTrtv() : "").append(Constants.QUOTE);
			exVamtLine.append(Constants.QUOTE)
					.append(exList.get(i).getExVamt() != null ? exList.get(i).getExVamt() : "").append(Constants.QUOTE);
			exVamtULine.append(Constants.QUOTE)
					.append(exList.get(i).getExVamtu() != null ? exList.get(i).getExVamtu() : "")
					.append(Constants.QUOTE);
			//exStdcLine.append("strftime(as.Date(\"" + exList.get(i).getsExStdtc() + "\"), \"%Y-%m-%dT%H:%M:%S\")");
			exStdcLine.append(Constants.QUOTE).append(exList.get(i).getsExStdtc()).append(Constants.QUOTE);
			exSdyLine.append(exList.get(i).getExStDy());

			if (i < exList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				exSeqLine.append(Constants.COMMA);
				exTrtLine.append(Constants.COMMA);
				exDoseLine.append(Constants.COMMA);
				exDosuLine.append(Constants.COMMA);
				exDoRfrmLine.append(Constants.COMMA);
				exDoRfqLine.append(Constants.COMMA);
				exRouteLine.append(Constants.COMMA);
				exLotLine.append(Constants.COMMA);
				exTrtVLine.append(Constants.COMMA);
				exVamtLine.append(Constants.COMMA);
				exVamtULine.append(Constants.COMMA);
				exStdcLine.append(Constants.COMMA);
				exSdyLine.append(Constants.COMMA);
			}
		}
	}
	
	private Set<Exposure> removeDuplicates(List<Exposure> data) {
		Set<Exposure> exSet = new LinkedHashSet<Exposure>();
		for (Exposure ex : data) {
			exSet.add(ex);
		}
		return exSet;
	}

}
