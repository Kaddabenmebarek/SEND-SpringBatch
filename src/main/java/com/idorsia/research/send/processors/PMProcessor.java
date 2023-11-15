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
import com.idorsia.research.send.domain.PalpableMasses;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("pmProcessor")
@Scope("step")
public class PMProcessor implements ItemProcessor<GenericDomain<PalpableMasses>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder pmSeqLine = new StringBuilder();
	private StringBuilder pmSpIddLine = new StringBuilder();
	private StringBuilder pmTestCdLine = new StringBuilder();
	private StringBuilder pmTestLine = new StringBuilder();
	private StringBuilder pmOrresLine = new StringBuilder();
	private StringBuilder pmOrresULine = new StringBuilder();
	private StringBuilder pmStresCLine = new StringBuilder();
	private StringBuilder pmStresNLine = new StringBuilder();
	private StringBuilder pmStresULine = new StringBuilder();
	private StringBuilder pmLocLine = new StringBuilder();
	private StringBuilder pmDtcLine = new StringBuilder();
	private StringBuilder pmDyLine = new StringBuilder();
	private StringBuilder pmNomDyLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<PalpableMasses> item) throws Exception {
		System.out.println("Calling PM PROCESSOR");
		StringBuilder rsb = generatePMScript("pm.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("pm", rsb.toString());
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

	private StringBuilder generatePMScript(String inputfileName, List<PalpableMasses> data)
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
				if (s.contains("$PMSEQ")) {
					s = StringUtils.replace(s, "$PMSEQ", pmSeqLine.toString());
				}
				if (s.contains("$PMSPID")) {
					s = StringUtils.replace(s, "$PMSPID", pmSpIddLine.toString());
				}
				if (s.contains("$CDPMTEST")) {
					s = StringUtils.replace(s, "$CDPMTEST", pmTestCdLine.toString());
				}
				if (s.contains("$PMTEST")) {
					s = StringUtils.replace(s, "$PMTEST", pmTestLine.toString());
				}
				if (s.contains("$PMORRES")) {
					s = StringUtils.replace(s, "$PMORRES", pmOrresLine.toString());
				}
				if (s.contains("$UPMORRES")) {
					s = StringUtils.replace(s, "$UPMORRES", pmOrresULine.toString());
				}
				if (s.contains("$PMSTRESC")) {
					s = StringUtils.replace(s, "$PMSTRESC", pmStresCLine.toString());
				}
				if (s.contains("$PMSTRESN")) {
					s = StringUtils.replace(s, "$PMSTRESN", pmStresNLine.toString());
				}
				if (s.contains("$PMSTRESU")) {
					s = StringUtils.replace(s, "$PMSTRESU", pmStresULine.toString());
				}
				if (s.contains("$PMLOC")) {
					s = StringUtils.replace(s, "$PMLOC", pmLocLine.toString());
				}
				if (s.contains("$PMDTC")) {
					s = StringUtils.replace(s, "$PMDTC", pmDtcLine.toString());
				}
				if (s.contains("$PMDY")) {
					s = StringUtils.replace(s, "$PMDY", pmDyLine.toString());
				}
				if (s.contains("$PMNOMDY")) {
					s = StringUtils.replace(s, "$PMNOMDY", pmNomDyLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/pm.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "pm.xpt";
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

	private void feedLines(List<PalpableMasses> pmList) {
		for (int i = 0; i < pmList.size(); i++) {
			PalpableMasses pm = pmList.get(i);
			studyIdLine.append(Constants.QUOTE).append(pm.getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(pm.getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(pm.getUsubjId()).append(Constants.QUOTE);
			pmSeqLine.append(i + 1);
			pmSpIddLine.append(Constants.QUOTE).append(pm.getPmSpId()).append(Constants.QUOTE);
			pmTestCdLine.append(Constants.QUOTE).append(pm.getPmTestcd()).append(Constants.QUOTE);
			pmTestLine.append(Constants.QUOTE).append(pm.getPmTest()).append(Constants.QUOTE);
			pmOrresLine.append(Constants.QUOTE).append(pm.getPmOrres()).append(Constants.QUOTE);
			pmOrresULine.append(Constants.QUOTE).append(pm.getPmOrresu()).append(Constants.QUOTE);
			pmStresCLine.append(Constants.QUOTE).append(pm.getPmStresc()).append(Constants.QUOTE);
			pmStresNLine.append(pm.getPmStresn());
			pmStresULine.append(Constants.QUOTE).append(pm.getPmStresu()).append(Constants.QUOTE);
			pmLocLine.append(Constants.QUOTE).append(pm.getPmLoc() != null ? pm.getPmLoc() : "")
					.append(Constants.QUOTE);
			// pmDtcLine.append("strftime(as.Date(\""+ data.get(i).getsPmDtc() +"\"),
			// \"%Y-%m-%dT%H:%M:%S\")");
			pmDtcLine.append(Constants.QUOTE).append(pm.getsPmDtc()).append(Constants.QUOTE);
			pmDyLine.append(pm.getPmDy());
			pmNomDyLine.append(pm.getPmNomDy());

			if (i < pmList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				pmSeqLine.append(Constants.COMMA);
				pmSpIddLine.append(Constants.COMMA);
				pmTestCdLine.append(Constants.COMMA);
				pmTestLine.append(Constants.COMMA);
				pmOrresLine.append(Constants.COMMA);
				pmOrresULine.append(Constants.COMMA);
				pmStresCLine.append(Constants.COMMA);
				pmStresNLine.append(Constants.COMMA);
				pmStresULine.append(Constants.COMMA);
				pmLocLine.append(Constants.COMMA);
				pmDtcLine.append(Constants.COMMA);
				pmDyLine.append(Constants.COMMA);
				pmNomDyLine.append(Constants.COMMA);
			}
		}
	}

}
