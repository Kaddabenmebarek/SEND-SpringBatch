package com.idorsia.research.send.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

import com.idorsia.research.send.domain.BodyWeights;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("bwProcessor")
@Scope("step")
public class BWProcessor implements ItemProcessor<GenericDomain<BodyWeights>, Map<String,String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder bwSeqLine = new StringBuilder();
	private StringBuilder bwTestCdLine = new StringBuilder();
	private StringBuilder bwTestLine = new StringBuilder();
	private StringBuilder bwOrresLine = new StringBuilder();
	private StringBuilder bwOrresULine = new StringBuilder();
	private StringBuilder bwStresCLine = new StringBuilder();
	private StringBuilder bwStresNLine = new StringBuilder();
	private StringBuilder bwStresULine = new StringBuilder();
	private StringBuilder bwBlFlLine = new StringBuilder();
	private StringBuilder bwDtcLine = new StringBuilder();
	private StringBuilder bwNomDyLine = new StringBuilder();
	private StringBuilder bwStatLine = new StringBuilder();

	@Override
	public Map<String,String> process(GenericDomain<BodyWeights> item) throws Exception {
		System.out.println("Calling BW PROCESSOR");
		StringBuilder rsb = generateBWScript("bw.R", item.getList());
		Map<String,String> res = new HashMap<String, String>();
		res.put("bw", rsb.toString());
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
	
	private StringBuilder generateBWScript(String inputfileName, List<BodyWeights> data)
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
				if (s.contains("$BWSEQ")) {
					s = StringUtils.replace(s, "$BWSEQ", bwSeqLine.toString());
				}
				if (s.contains("$BWTESTCD")) {
					s = StringUtils.replace(s, "$BWTESTCD", bwTestCdLine.toString());
				}
				if (s.contains("$BWTEST")) {
					s = StringUtils.replace(s, "$BWTEST", bwTestLine.toString());
				}
				if (s.contains("$BWORRES")) {
					s = StringUtils.replace(s, "$BWORRES", bwOrresLine.toString());
				}
				if (s.contains("$UBWORRES")) {
					s = StringUtils.replace(s, "$UBWORRES", bwOrresULine.toString());
				}
				if (s.contains("$BWSTRESC")) {
					s = StringUtils.replace(s, "$BWSTRESC", bwStresCLine.toString());
				}
				if (s.contains("$BWSTRESC")) {
					s = StringUtils.replace(s, "$BWSTRESC", bwStresCLine.toString());
				}
				if (s.contains("$BWSTRESN")) {
					s = StringUtils.replace(s, "$BWSTRESN", bwStresNLine.toString());
				}
				if (s.contains("$BWSTRESU")) {
					s = StringUtils.replace(s, "$BWSTRESU", bwStresULine.toString());
				}
				if (s.contains("$BWBLFL")) {
					s = StringUtils.replace(s, "$BWBLFL", bwBlFlLine.toString());
				}
				if (s.contains("$BWDTC")) {
					s = StringUtils.replace(s, "$BWDTC", bwDtcLine.toString());
				}
				if (s.contains("$BWNOMDY")) {
					s = StringUtils.replace(s, "$BWNOMDY", bwNomDyLine.toString());
				}
				if (s.contains("$BWSTAT")) {
					s = StringUtils.replace(s, "$BWSTAT", bwStatLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/bw.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "bw.xpt";
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

	private void feedLines(List<BodyWeights> data) {
		List<BodyWeights> finalBWList = new ArrayList<BodyWeights>(removeDuplicates(data));
		for (int i = 0; i < finalBWList.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(finalBWList.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(finalBWList.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(finalBWList.get(i).getUsubjId()).append(Constants.QUOTE);
			bwSeqLine.append(i);
			bwTestCdLine.append(Constants.QUOTE).append(finalBWList.get(i).getBwTestCD()).append(Constants.QUOTE);
			bwTestLine.append(Constants.QUOTE).append(finalBWList.get(i).getBwTest()).append(Constants.QUOTE);
			bwOrresLine.append(finalBWList.get(i).getBwOrres());
			bwOrresULine.append(Constants.QUOTE).append(finalBWList.get(i).getBwOrresu()).append(Constants.QUOTE);
			bwStresCLine.append(finalBWList.get(i).getBwSTresc());
			bwStresNLine.append(finalBWList.get(i).getBwSTresn());
			bwStresULine.append(Constants.QUOTE).append(finalBWList.get(i).getBwSTresu()).append(Constants.QUOTE);
			bwBlFlLine.append(Constants.QUOTE).append(finalBWList.get(i).getBwSBlfl()).append(Constants.QUOTE);
			bwDtcLine.append(finalBWList.get(i).getSbwDtc() != null
					? "strftime(as.Date(\"" + finalBWList.get(i).getSbwDtc() + "\"), \"%Y-%m-%d\")"
					: "NA");
			bwNomDyLine.append(finalBWList.get(i).getBwnomdy());
			bwStatLine.append(Constants.QUOTE).append(finalBWList.get(i).getBwStat()).append(Constants.QUOTE);

			if (i < finalBWList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				bwSeqLine.append(Constants.COMMA);
				bwTestCdLine.append(Constants.COMMA);
				bwTestLine.append(Constants.COMMA);
				bwOrresLine.append(Constants.COMMA);
				bwOrresULine.append(Constants.COMMA);
				bwStresCLine.append(Constants.COMMA);
				bwStresNLine.append(Constants.COMMA);
				bwStresULine.append(Constants.COMMA);
				bwBlFlLine.append(Constants.COMMA);
				bwDtcLine.append(Constants.COMMA);
				bwNomDyLine.append(Constants.COMMA);
				bwStatLine.append(Constants.COMMA);
			}
		}
	}

	private Set<BodyWeights> removeDuplicates(List<BodyWeights> bwList) {
		Set<BodyWeights> bwSet = new LinkedHashSet<BodyWeights>();
		for (BodyWeights bw : bwList) {
			bwSet.add(bw);
		}
		return bwSet;
	}
}
