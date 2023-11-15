package com.idorsia.research.send.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.ClinicalSigns;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("clProcessor")
@Scope("step")
public class CLProcessor implements ItemProcessor<GenericDomain<ClinicalSigns>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder clSeqLine = new StringBuilder();
	private StringBuilder clTestCdLine = new StringBuilder();
	private StringBuilder clTestLine = new StringBuilder();
	private StringBuilder clCatLine = new StringBuilder();
	private StringBuilder clOrresLine = new StringBuilder();
	private StringBuilder clStresCLine = new StringBuilder();
	private StringBuilder clLocLine = new StringBuilder();
	private StringBuilder visidityLine = new StringBuilder();
	private StringBuilder clDtcLine = new StringBuilder();
	private StringBuilder clDyLine = new StringBuilder();
	private StringBuilder clNomDyLine = new StringBuilder();
	private StringBuilder clTptLine = new StringBuilder();
	private StringBuilder clTpNumtLine = new StringBuilder();
	private StringBuilder clStatLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<ClinicalSigns> item) throws Exception {
		System.out.println("Calling CL PROCESSOR");
		StringBuilder rsb = generateCLScript("cl.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("cl", rsb.toString());
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

	private StringBuilder generateCLScript(String inputfileName, List<ClinicalSigns> data)
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
				if (s.contains("$CLSEQ")) {
					s = StringUtils.replace(s, "$CLSEQ", clSeqLine.toString());
				}
				if (s.contains("$CLTESTCD")) {
					s = StringUtils.replace(s, "$CLTESTCD", clTestCdLine.toString());
				}
				if (s.contains("$CLTEST")) {
					s = StringUtils.replace(s, "$CLTEST", clTestLine.toString());
				}
				if (s.contains("$CLCAT")) {
					s = StringUtils.replace(s, "$CLCAT", clCatLine.toString());
				}
				if (s.contains("$CLORRES")) {
					s = StringUtils.replace(s, "$CLORRES", clOrresLine.toString());
				}
				if (s.contains("$CLSTRESC")) {
					s = StringUtils.replace(s, "$CLSTRESC", clStresCLine.toString());
				}
				if (s.contains("$CLLOC")) {
					s = StringUtils.replace(s, "$CLLOC", clLocLine.toString());
				}
				if (s.contains("$VISITDY")) {
					s = StringUtils.replace(s, "$VISITDY", visidityLine.toString());
				}
				if (s.contains("$CLDTC")) {
					s = StringUtils.replace(s, "$CLDTC", clDtcLine.toString());
				}
				if (s.contains("$CLDY")) {
					s = StringUtils.replace(s, "$CLDY", clDyLine.toString());
				}
				if (s.contains("$CLNOMDY")) {
					s = StringUtils.replace(s, "$CLNOMDY", clNomDyLine.toString());
				}
				if (s.contains("$CLTPT")) {
					s = StringUtils.replace(s, "$CLTPT", clTptLine.toString());
				}
				if (s.contains("$NUMCLTPT")) {
					s = StringUtils.replace(s, "$NUMCLTPT", clTpNumtLine.toString());
				}
				if (s.contains("$CLSTAT")) {
					s = StringUtils.replace(s, "$CLSTAT", clStatLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/cl.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "cl.xpt";
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

	private void feedLines(List<ClinicalSigns> clinSigns) {

		for (int i = 0; i < clinSigns.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(clinSigns.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(clinSigns.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(clinSigns.get(i).getUsubjId()).append(Constants.QUOTE);
			clSeqLine.append(i);
			clTestCdLine.append(Constants.QUOTE).append(clinSigns.get(i).getClTestCd()).append(Constants.QUOTE);
			clTestLine.append(Constants.QUOTE).append(clinSigns.get(i).getClTest()).append(Constants.QUOTE);
			clCatLine.append(Constants.QUOTE).append(clinSigns.get(i).getClCat()).append(Constants.QUOTE);
			clOrresLine.append(Constants.QUOTE).append(clinSigns.get(i).getClOrres()).append(Constants.QUOTE);
			clStresCLine.append(Constants.QUOTE).append(clinSigns.get(i).getClStresc()).append(Constants.QUOTE);
			String localization = clinSigns.get(i).getClLoc() != null ? clinSigns.get(i).getClLoc() : "";
			clLocLine.append(Constants.QUOTE).append(localization).append(Constants.QUOTE);
			visidityLine.append(clinSigns.get(i).getVisitdy());
			// clDtcLine.append(clinSigns.get(i).getClDtc() != null ? "strftime(as.Date(\""+
			// clinSigns.get(i).getsClDtc() +"\"), \"%Y-%m-%dT%H:%M:%S\")" : "NA");
			clDtcLine.append(Constants.QUOTE).append(clinSigns.get(i).getsClDtc()).append(Constants.QUOTE);
			clDyLine.append(clinSigns.get(i).getClDy());
			clNomDyLine.append(clinSigns.get(i).getClNomdy());
			clTptLine.append(Constants.QUOTE).append(clinSigns.get(i).getClTpt()).append(Constants.QUOTE);
			clTpNumtLine.append(clinSigns.get(i).getClTptnum());
			clStatLine.append(Constants.QUOTE).append(clinSigns.get(i).getClstat()).append(Constants.QUOTE);

			if (i < clinSigns.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				clSeqLine.append(Constants.COMMA);
				clTestCdLine.append(Constants.COMMA);
				clTestLine.append(Constants.COMMA);
				clCatLine.append(Constants.COMMA);
				clOrresLine.append(Constants.COMMA);
				clStresCLine.append(Constants.COMMA);
				clLocLine.append(Constants.COMMA);
				visidityLine.append(Constants.COMMA);
				clDtcLine.append(Constants.COMMA);
				clDyLine.append(Constants.COMMA);
				clNomDyLine.append(Constants.COMMA);
				clTptLine.append(Constants.COMMA);
				clTpNumtLine.append(Constants.COMMA);
				clStatLine.append(Constants.COMMA);
			}
		}
	}

}
