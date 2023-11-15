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

import com.idorsia.research.send.domain.FoodWater;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("fwProcessor")
@Scope("step")
public class FWProcessor implements ItemProcessor<GenericDomain<FoodWater>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder poolIdLine = new StringBuilder();
	private StringBuilder fwSeqLine = new StringBuilder();
	private StringBuilder fwTestCdLine = new StringBuilder();
	private StringBuilder fwTestLine = new StringBuilder();
	private StringBuilder fwOrresLine = new StringBuilder();
	private StringBuilder fwOrresULine = new StringBuilder();
	private StringBuilder fwStresCLine = new StringBuilder();
	private StringBuilder fwStresNLine = new StringBuilder();
	private StringBuilder fwStresULine = new StringBuilder();
	private StringBuilder fwDtcLine = new StringBuilder();
	private StringBuilder fwEnDtcLine = new StringBuilder();
	private StringBuilder fwDyLine = new StringBuilder();
	private StringBuilder fwEnDyLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<FoodWater> item) throws Exception {
		System.out.println("Calling FW PROCESSOR");
		StringBuilder rsb = generateFWScript("fw.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("fw", rsb.toString());
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

	private StringBuilder generateFWScript(String inputfileName, List<FoodWater> data)
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
				if (s.contains("$POOLID")) {
					s = StringUtils.replace(s, "$POOLID", poolIdLine.toString());
				}
				if (s.contains("$FWSEQ")) {
					s = StringUtils.replace(s, "$FWSEQ", fwSeqLine.toString());
				}
				if (s.contains("$FWTESTCD")) {
					s = StringUtils.replace(s, "$FWTESTCD", fwTestCdLine.toString());
				}
				if (s.contains("$FWTEST")) {
					s = StringUtils.replace(s, "$FWTEST", fwTestLine.toString());
				}
				if (s.contains("$FWORRES")) {
					s = StringUtils.replace(s, "$FWORRES", fwOrresLine.toString());
				}
				if (s.contains("$UFWORRES")) {
					s = StringUtils.replace(s, "$UFWORRES", fwOrresULine.toString());
				}
				if (s.contains("$FWSTRESC")) {
					s = StringUtils.replace(s, "$FWSTRESC", fwStresCLine.toString());
				}
				if (s.contains("$FWSTRESN")) {
					s = StringUtils.replace(s, "$FWSTRESN", fwStresNLine.toString());
				}
				if (s.contains("$FWSTRESU")) {
					s = StringUtils.replace(s, "$FWSTRESU", fwStresULine.toString());
				}
				if (s.contains("$FWDTC")) {
					s = StringUtils.replace(s, "$FWDTC", fwDtcLine.toString());
				}
				if (s.contains("$FWENDTC")) {
					s = StringUtils.replace(s, "$FWENDTC", fwEnDtcLine.toString());
				}
				if (s.contains("$FWDY")) {
					s = StringUtils.replace(s, "$FWDY", fwDyLine.toString());
				}
				if (s.contains("$FWENDY")) {
					s = StringUtils.replace(s, "$FWENDY", fwEnDyLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/fw.xpt";
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

	private void feedLines(List<FoodWater> fwList) {
		for (int i = 0; i < fwList.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(fwList.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(fwList.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(fwList.get(i).getUsubjId()).append(Constants.QUOTE);
			poolIdLine.append(Constants.QUOTE).append(fwList.get(i).getPoolId()).append(Constants.QUOTE);
			fwSeqLine.append(i);
			fwTestCdLine.append(Constants.QUOTE).append(fwList.get(i).getFwTestCd()).append(Constants.QUOTE);
			fwTestLine.append(Constants.QUOTE).append(fwList.get(i).getFwTest()).append(Constants.QUOTE);
			fwOrresLine.append(fwList.get(i).getFwOrres());
			fwOrresULine.append(Constants.QUOTE).append(fwList.get(i).getFwOrresu()).append(Constants.QUOTE);
			fwStresCLine.append(fwList.get(i).getFwStresc());
			fwStresNLine.append(fwList.get(i).getFwStresn());
			fwStresULine.append(Constants.QUOTE).append(fwList.get(i).getFwStresu()).append(Constants.QUOTE);

			fwDtcLine.append("strftime(as.Date(\"" + fwList.get(i).getsFwDtc() + "\"), \"%Y-%m-%d\")");
			if (fwList.get(i).getFwEndtc() != null) {
				fwEnDtcLine.append("strftime(as.Date(\"" + fwList.get(i).getsFwEndtc() + "\"), \"%Y-%m-%d\")");
			}
			fwDyLine.append(fwList.get(i).getFwDy());
			fwEnDyLine.append(fwList.get(i).getFwEndy());

			if (i < fwList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				poolIdLine.append(Constants.COMMA);
				fwSeqLine.append(Constants.COMMA);
				fwTestCdLine.append(Constants.COMMA);
				fwTestLine.append(Constants.COMMA);
				fwOrresLine.append(Constants.COMMA);
				fwOrresULine.append(Constants.COMMA);
				fwStresCLine.append(Constants.COMMA);
				fwStresNLine.append(Constants.COMMA);
				fwStresULine.append(Constants.COMMA);
				fwDtcLine.append(Constants.COMMA);
				fwEnDtcLine.append(Constants.COMMA);
				fwDyLine.append(Constants.COMMA);
				fwEnDyLine.append(Constants.COMMA);
			}
		}
	}

}
