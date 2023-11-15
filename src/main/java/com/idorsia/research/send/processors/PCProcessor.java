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
import com.idorsia.research.send.domain.PharmacokineticsConcentrations;

@Component("pcProcessor")
@Scope("step")
public class PCProcessor implements ItemProcessor<GenericDomain<PharmacokineticsConcentrations>, Map<String, String>> {

	private String studyId;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder pcSeqLine = new StringBuilder();
	private StringBuilder pcTestCdLine = new StringBuilder();
	private StringBuilder pcTestLine = new StringBuilder();
	private StringBuilder pcCatLine = new StringBuilder();
	private StringBuilder pcOrresLine = new StringBuilder();
	private StringBuilder pcOrresULine = new StringBuilder();
	private StringBuilder pcStresCLine = new StringBuilder();
	private StringBuilder pcStresNLine = new StringBuilder();
	private StringBuilder pcStresULine = new StringBuilder();
	private StringBuilder pcNamLine = new StringBuilder();
	private StringBuilder pcSpecLine = new StringBuilder();
	private StringBuilder pcBlFlLine = new StringBuilder();
	private StringBuilder pcLloqLine = new StringBuilder();
	private StringBuilder pcDtcLine = new StringBuilder();
	private StringBuilder pcRfDtcLine = new StringBuilder();
	private StringBuilder pcNomDyLine = new StringBuilder();
	private StringBuilder pcStatLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<PharmacokineticsConcentrations> item) throws Exception {
		System.out.println("Calling PC PROCESSOR");
		StringBuilder rsb = generatePCScript("pc.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("pc", rsb.toString());
		return res;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
		try {
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			outputPath = rb.getString("output_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder generatePCScript(String inputfileName, List<PharmacokineticsConcentrations> data)
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
				if (s.contains("$PCSEQ")) {
					s = StringUtils.replace(s, "$PCSEQ", pcSeqLine.toString());
				}
				if (s.contains("$CDPCTEST")) {
					s = StringUtils.replace(s, "$CDPCTEST", pcTestCdLine.toString());
				}
				if (s.contains("$PCTEST")) {
					s = StringUtils.replace(s, "$PCTEST", pcTestLine.toString());
				}
				if (s.contains("$PCCAT")) {
					s = StringUtils.replace(s, "$PCCAT", pcCatLine.toString());
				}
				if (s.contains("$PCORRES")) {
					s = StringUtils.replace(s, "$PCORRES", pcOrresLine.toString());
				}
				if (s.contains("$UPCORRES")) {
					s = StringUtils.replace(s, "$UPCORRES", pcOrresULine.toString());
				}
				if (s.contains("$PCSTRESC")) {
					s = StringUtils.replace(s, "$PCSTRESC", pcStresCLine.toString());
				}
				if (s.contains("$PCSTRESN")) {
					s = StringUtils.replace(s, "$PCSTRESN", pcStresNLine.toString());
				}
				if (s.contains("$PCSTRESU")) {
					s = StringUtils.replace(s, "$PCSTRESU", pcStresULine.toString());
				}
				if (s.contains("$PCNAM")) {
					s = StringUtils.replace(s, "$PCNAM", pcNamLine.toString());
				}
				if (s.contains("$PCSPEC")) {
					s = StringUtils.replace(s, "$PCSPEC", pcSpecLine.toString());
				}
				if (s.contains("$PCBLFL")) {
					s = StringUtils.replace(s, "$PCBLFL", pcBlFlLine.toString());
				}
				if (s.contains("$PCLLOQ")) {
					s = StringUtils.replace(s, "$PCLLOQ", pcLloqLine.toString());
				}
				if (s.contains("$PCDTC")) {
					s = StringUtils.replace(s, "$PCDTC", pcDtcLine.toString());
				}
				if (s.contains("$PCRFTDTC")) {
					s = StringUtils.replace(s, "$PCRFTDTC", pcRfDtcLine.toString());
				}
				if (s.contains("$PCNOMDY")) {
					s = StringUtils.replace(s, "$PCNOMDY", pcNomDyLine.toString());
				}
				if (s.contains("$PCSTAT")) {
					s = StringUtils.replace(s, "$PCSTAT", pcStatLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + studyId + "/pc.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "pc.xpt";
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

	private void feedLines(List<PharmacokineticsConcentrations> pcList) {
		for (int i = 0; i < pcList.size(); i++) {
			PharmacokineticsConcentrations pc = pcList.get(i);
			studyIdLine.append(Constants.QUOTE).append(pc.getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(pc.getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(pc.getUsubjId()).append(Constants.QUOTE);
			pcSeqLine.append(i+1);
			pcTestCdLine.append(Constants.QUOTE).append(pc.getPcTestcd()).append(Constants.QUOTE);
			pcTestLine.append(Constants.QUOTE).append(pc.getPcTest()).append(Constants.QUOTE);
			pcCatLine.append(Constants.QUOTE).append(pc.getPcCat()).append(Constants.QUOTE);
			if (pc.getPcOrres() != null) {
				pcOrresLine.append(Constants.QUOTE).append(pc.getPcOrres()).append(Constants.QUOTE);
				pcStresCLine.append(Constants.QUOTE).append(pc.getPcStresc()).append(Constants.QUOTE);
				pcStresNLine.append(pc.getPcStresn());
			} else {
				pcOrresLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				pcStresCLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				pcStresNLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			}
			pcOrresULine.append(Constants.QUOTE).append(pc.getPcOrresu()).append(Constants.QUOTE);
			pcStresULine.append(Constants.QUOTE).append(pc.getPcStresu()).append(Constants.QUOTE);
			pcNamLine.append(Constants.QUOTE).append(pc.getPcStresu()).append(Constants.QUOTE);
			pcSpecLine.append(Constants.QUOTE).append(pc.getPcStresu()).append(Constants.QUOTE);
			pcBlFlLine.append(Constants.QUOTE).append(pc.getPcBlFl()).append(Constants.QUOTE);
			pcLloqLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			//pcDtcLine.append(pc.getsPcDtc() != null ? "strftime(as.Date(\"" + pc.getsPcDtc() + "\"), \"%Y-%m-%d\")" : "NA");
			pcDtcLine.append(Constants.QUOTE).append(pc.getsPcDtc()).append(Constants.QUOTE);
			pcRfDtcLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			pcNomDyLine.append(pc.getPcNomdy());
			pcStatLine.append(Constants.QUOTE).append(pc.getPcStat()).append(Constants.QUOTE);

			if (i < pcList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				pcSeqLine.append(Constants.COMMA);
				pcTestCdLine.append(Constants.COMMA);
				pcTestLine.append(Constants.COMMA);
				pcCatLine.append(Constants.COMMA);
				pcOrresLine.append(Constants.COMMA);
				pcOrresULine.append(Constants.COMMA);
				pcStresCLine.append(Constants.COMMA);
				pcStresNLine.append(Constants.COMMA);
				pcStresULine.append(Constants.COMMA);
				pcNamLine.append(Constants.COMMA);
				pcSpecLine.append(Constants.COMMA);
				pcBlFlLine.append(Constants.COMMA);
				pcLloqLine.append(Constants.COMMA);
				pcDtcLine.append(Constants.COMMA);
				pcRfDtcLine.append(Constants.COMMA);
				pcNomDyLine.append(Constants.COMMA);
				pcStatLine.append(Constants.COMMA);
			}
		}
	}

}
