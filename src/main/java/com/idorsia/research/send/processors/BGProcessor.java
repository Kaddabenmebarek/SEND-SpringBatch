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

import com.idorsia.research.send.domain.BodyWeightGain;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("bgProcessor")
@Scope("step")
public class BGProcessor implements ItemProcessor<GenericDomain<BodyWeightGain>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder bgSeqLine = new StringBuilder();
	private StringBuilder bgTestCdLine = new StringBuilder();
	private StringBuilder bgTestLine = new StringBuilder();
	private StringBuilder bgOrresLine = new StringBuilder();
	private StringBuilder bgOrresULine = new StringBuilder();
	private StringBuilder bgStresCLine = new StringBuilder();
	private StringBuilder bgStresNLine = new StringBuilder();
	private StringBuilder bgStresULine = new StringBuilder();
	private StringBuilder bgDtcLine = new StringBuilder();
	private StringBuilder bgEnDtcLine = new StringBuilder();
	private StringBuilder bgDyLine = new StringBuilder();
	private StringBuilder bgEnDyLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<BodyWeightGain> item) throws Exception {
		System.out.println("Calling BG PROCESSOR");
		StringBuilder rsb = generateBGScript("bg.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("bg", rsb.toString());
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

	private StringBuilder generateBGScript(String inputfileName, List<BodyWeightGain> data)
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
				if (s.contains("$BGSEQ")) {
					s = StringUtils.replace(s, "$BGSEQ", bgSeqLine.toString());
				}
				if (s.contains("$BGTESTCD")) {
					s = StringUtils.replace(s, "$BGTESTCD", bgTestCdLine.toString());
				}
				if (s.contains("$BGTEST")) {
					s = StringUtils.replace(s, "$BGTEST", bgTestLine.toString());
				}
				if (s.contains("$BGORRES")) {
					s = StringUtils.replace(s, "$BGORRES", bgOrresLine.toString());
				}
				if (s.contains("$UBGORRES")) {
					s = StringUtils.replace(s, "$UBGORRES", bgOrresULine.toString());
				}
				if (s.contains("$BGSTRESC")) {
					s = StringUtils.replace(s, "$BGSTRESC", bgStresCLine.toString());
				}
				if (s.contains("$BGSTRESN")) {
					s = StringUtils.replace(s, "$BGSTRESN", bgStresNLine.toString());
				}
				if (s.contains("$BGSTRESU")) {
					s = StringUtils.replace(s, "$BGSTRESU", bgStresULine.toString());
				}
				if (s.contains("$BGDTC")) {
					s = StringUtils.replace(s, "$BGDTC", bgDtcLine.toString());
				}
				if (s.contains("$BGENDTC")) {
					s = StringUtils.replace(s, "$BGENDTC", bgEnDtcLine.toString());
				}
				if (s.contains("$BGDY")) {
					s = StringUtils.replace(s, "$BGDY", bgDyLine.toString());
				}
				if (s.contains("$BGENDY")) {
					s = StringUtils.replace(s, "$BGENDY", bgEnDyLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/bg.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "bg.xpt";
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

	private void feedLines(List<BodyWeightGain> bWGList) {

		for (int i = 0; i < bWGList.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(bWGList.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(bWGList.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(bWGList.get(i).getUsubjId()).append(Constants.QUOTE);
			bgSeqLine.append(i + 1);
			bgTestCdLine.append(Constants.QUOTE).append(bWGList.get(i).getBgTestCd()).append(Constants.QUOTE);
			bgTestLine.append(Constants.QUOTE).append(bWGList.get(i).getBgTest()).append(Constants.QUOTE);
			bgOrresLine.append(bWGList.get(i).getBgOrres());
			bgOrresULine.append(Constants.QUOTE).append(bWGList.get(i).getBgOrresu()).append(Constants.QUOTE);
			bgStresCLine.append(bWGList.get(i).getBgStresc());
			bgStresNLine.append(bWGList.get(i).getBgStresn());
			bgStresULine.append(Constants.QUOTE).append(bWGList.get(i).getBgStresu()).append(Constants.QUOTE);
			bgDtcLine.append("strftime(as.Date(\"" + bWGList.get(i).getSbgDtc() + "\"), \"%Y-%m-%d\")");
			bgEnDtcLine.append("strftime(as.Date(\"" + bWGList.get(i).getSbgEnDtc() + "\"), \"%Y-%m-%d\")");
			bgDyLine.append(bWGList.get(i).getBgDy());
			bgEnDyLine.append(bWGList.get(i).getBgEndy());

			if (i < bWGList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				bgSeqLine.append(Constants.COMMA);
				bgTestCdLine.append(Constants.COMMA);
				bgTestLine.append(Constants.COMMA);
				bgOrresLine.append(Constants.COMMA);
				bgOrresULine.append(Constants.COMMA);
				bgStresCLine.append(Constants.COMMA);
				bgStresNLine.append(Constants.COMMA);
				bgStresULine.append(Constants.COMMA);
				bgDtcLine.append(Constants.COMMA);
				bgEnDtcLine.append(Constants.COMMA);
				bgDyLine.append(Constants.COMMA);
				bgEnDyLine.append(Constants.COMMA);
			}
		}
	}
}
