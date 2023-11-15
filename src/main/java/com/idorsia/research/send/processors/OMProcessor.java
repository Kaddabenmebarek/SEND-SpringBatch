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

import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.OrganMeasurements;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("omProcessor")
@Scope("step")
public class OMProcessor implements ItemProcessor<GenericDomain<OrganMeasurements>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder omSeqLine = new StringBuilder();
	private StringBuilder omTestcdLine = new StringBuilder();
	private StringBuilder omTestLine = new StringBuilder();
	private StringBuilder omOrresLine = new StringBuilder();
	private StringBuilder omOrresULine = new StringBuilder();
	private StringBuilder omStresCLine = new StringBuilder();
	private StringBuilder omStresNLine = new StringBuilder();
	private StringBuilder omStresULine = new StringBuilder();
	private StringBuilder omSpecLine = new StringBuilder();
	private StringBuilder omDtcLine = new StringBuilder();
	private StringBuilder omDyLine = new StringBuilder();
	private StringBuilder omNomDyLine = new StringBuilder();
	private StringBuilder omStatLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<OrganMeasurements> item) throws Exception {
		System.out.println("Calling OM PROCESSOR");
		StringBuilder rsb = generateOMScript("om.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("om", rsb.toString());
		return res;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
		stepExecution.getJobExecution().getExecutionContext().remove(Constants.STUDY);
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

	private StringBuilder generateOMScript(String inputfileName, List<OrganMeasurements> data)
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
				if (s.contains("$OMSEQ")) {
					s = StringUtils.replace(s, "$OMSEQ", omSeqLine.toString());
				}
				if (s.contains("$CDOMTEST")) {
					s = StringUtils.replace(s, "$CDOMTEST", omTestcdLine.toString());
				}
				if (s.contains("$OMTEST")) {
					s = StringUtils.replace(s, "$OMTEST", omTestLine.toString());
				}
				if (s.contains("$OMORRES")) {
					s = StringUtils.replace(s, "$OMORRES", omOrresLine.toString());
				}
				if (s.contains("$UOMORRES")) {
					s = StringUtils.replace(s, "$UOMORRES", omOrresULine.toString());
				}
				if (s.contains("$OMSTRESC")) {
					s = StringUtils.replace(s, "$OMSTRESC", omStresCLine.toString());
				}
				if (s.contains("$OMSTRESN")) {
					s = StringUtils.replace(s, "$OMSTRESN", omStresNLine.toString());
				}
				if (s.contains("$OMSTRESU")) {
					s = StringUtils.replace(s, "$OMSTRESU", omStresULine.toString());
				}
				if (s.contains("$OMSPEC")) {
					s = StringUtils.replace(s, "$OMSPEC", omSpecLine.toString());
				}
				if (s.contains("$OMDTC")) {
					s = StringUtils.replace(s, "$OMDTC", omDtcLine.toString());
				}
				if (s.contains("$OMDY")) {
					s = StringUtils.replace(s, "$OMDY", omDyLine.toString());
				}
				if (s.contains("$OMNOMDY")) {
					s = StringUtils.replace(s, "$OMNOMDY", omNomDyLine.toString());
				}
				if (s.contains("$OMSTAT")) {
					s = StringUtils.replace(s, "$OMSTAT", omStatLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/om.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "om.xpt";
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

	private void feedLines(List<OrganMeasurements> data) {
		for (int i = 0; i < data.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(data.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(data.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(data.get(i).getUsubjId()).append(Constants.QUOTE);
			omSeqLine.append(i);
			omTestcdLine.append(Constants.QUOTE).append(data.get(i).getOmTestcd()).append(Constants.QUOTE);
			omTestLine.append(Constants.QUOTE).append(data.get(i).getOmTest()).append(Constants.QUOTE);
			if (data.get(i).getOmOrres() != null) {
				omOrresLine.append(Constants.QUOTE).append(data.get(i).getOmOrres()).append(Constants.QUOTE);
				omStresCLine.append(Constants.QUOTE).append(data.get(i).getOmStresc()).append(Constants.QUOTE);
				omStresNLine.append(data.get(i).getOmStresn());
			} else {
				omOrresLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				omStresCLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				omStresNLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			}
			omOrresULine.append(Constants.QUOTE).append(data.get(i).getOmOrresu()).append(Constants.QUOTE);
			omStresULine.append(Constants.QUOTE).append(data.get(i).getOmStresu()).append(Constants.QUOTE);
			omSpecLine.append(Constants.QUOTE).append(data.get(i).getOmSpec()).append(Constants.QUOTE);
			omDtcLine.append("strftime(as.Date(\"" + data.get(i).getOmSDtc() + "\"), \"%Y-%m-%d\")");
			omDyLine.append(data.get(i).getOmDy());
			omNomDyLine.append(data.get(i).getOmNomdy());
			omStatLine.append(Constants.QUOTE).append(data.get(i).getOmStat()).append(Constants.QUOTE);

			if (i < data.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				omSeqLine.append(Constants.COMMA);
				omTestcdLine.append(Constants.COMMA);
				omTestLine.append(Constants.COMMA);
				omOrresLine.append(Constants.COMMA);
				omOrresULine.append(Constants.COMMA);
				omStresCLine.append(Constants.COMMA);
				omStresNLine.append(Constants.COMMA);
				omStresULine.append(Constants.COMMA);
				omSpecLine.append(Constants.COMMA);
				omDtcLine.append(Constants.COMMA);
				omDyLine.append(Constants.COMMA);
				omNomDyLine.append(Constants.COMMA);
				omStatLine.append(Constants.COMMA);
			}
		}
	}

}
