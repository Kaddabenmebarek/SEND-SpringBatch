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
import com.idorsia.research.send.domain.VitalSigns;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("vsProcessor")
@Scope("step")
public class VSProcessor implements ItemProcessor<GenericDomain<VitalSigns>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder vsSeqLine = new StringBuilder();
	private StringBuilder vsGrpIdLine = new StringBuilder();
	private StringBuilder vsRefIdLine = new StringBuilder();
	private StringBuilder vsTestCdLine = new StringBuilder();
	private StringBuilder vsTestLine = new StringBuilder();
	private StringBuilder vsOrresLine = new StringBuilder();
	private StringBuilder vsOrresULine = new StringBuilder();
	private StringBuilder vsStresCLine = new StringBuilder();
	private StringBuilder vsStresNLine = new StringBuilder();
	private StringBuilder vsStresULine = new StringBuilder();
	private StringBuilder vsBlFlLine = new StringBuilder();
	private StringBuilder vsDtcLine = new StringBuilder();
	private StringBuilder vsDyLine = new StringBuilder();
	private StringBuilder vsNomDyLine = new StringBuilder();
	private StringBuilder vsStatLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<VitalSigns> item) throws Exception {
		System.out.println("Calling VS PROCESSOR");
		StringBuilder rsb = generateVSScript("vs.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("vs", rsb.toString());
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

	private StringBuilder generateVSScript(String inputfileName, List<VitalSigns> data)
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
				if (s.contains("$VSSEQ")) {
					s = StringUtils.replace(s, "$VSSEQ", vsSeqLine.toString());
				}
				if (s.contains("$CDVSTEST")) {
					s = StringUtils.replace(s, "$CDVSTEST", vsTestCdLine.toString());
				}
				if (s.contains("$VSTEST")) {
					s = StringUtils.replace(s, "$VSTEST", vsTestLine.toString());
				}
				if (s.contains("$VSORRES")) {
					s = StringUtils.replace(s, "$VSORRES", vsOrresLine.toString());
				}
				if (s.contains("$UVSORRES")) {
					s = StringUtils.replace(s, "$UVSORRES", vsOrresULine.toString());
				}
				if (s.contains("$VSSTRESC")) {
					s = StringUtils.replace(s, "$VSSTRESC", vsStresCLine.toString());
				}
				if (s.contains("$VSSTRESN")) {
					s = StringUtils.replace(s, "$VSSTRESN", vsStresNLine.toString());
				}
				if (s.contains("$VSSTRESU")) {
					s = StringUtils.replace(s, "$VSSTRESU", vsStresULine.toString());
				}
				if (s.contains("$VSBLFL")) {
					s = StringUtils.replace(s, "$VSBLFL", vsBlFlLine.toString());
				}
				if (s.contains("$VSDTC")) {
					s = StringUtils.replace(s, "$VSDTC", vsDtcLine.toString());
				}
				if (s.contains("$VSDY")) {
					s = StringUtils.replace(s, "$VSDY", vsDyLine.toString());
				}
				if (s.contains("$VSNOMDY")) {
					s = StringUtils.replace(s, "$VSNOMDY", vsNomDyLine.toString());
				}
				if (s.contains("$VSSTAT")) {
					s = StringUtils.replace(s, "$VSSTAT", vsStatLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/vs.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "vs.xpt";
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

	private void feedLines(List<VitalSigns> vsList) {
		for (int i = 0; i < vsList.size(); i++) {
			VitalSigns vs = vsList.get(i);
			studyIdLine.append(Constants.QUOTE).append(vs.getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(vs.getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(vs.getUsubjId()).append(Constants.QUOTE);
			vsSeqLine.append(i);
			vsTestCdLine.append(Constants.QUOTE).append(vs.getVsTestcd()).append(Constants.QUOTE);
			vsTestLine.append(Constants.QUOTE).append(vs.getVsTest()).append(Constants.QUOTE);
			if (vs.getVsOrres() != null) {
				vsOrresLine.append(Constants.QUOTE).append(vs.getVsOrres()).append(Constants.QUOTE);
				vsStresCLine.append(Constants.QUOTE).append(vs.getVsStresc()).append(Constants.QUOTE);
				vsStresNLine.append(vs.getVsStresn());
			} else {
				vsOrresLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				vsStresCLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
				vsStresNLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			}
			vsOrresULine.append(Constants.QUOTE).append(vs.getVsOrresu()).append(Constants.QUOTE);
			vsStresULine.append(Constants.QUOTE).append(vs.getVsStresu()).append(Constants.QUOTE);
			vsBlFlLine.append(Constants.QUOTE).append(vs.getVsBlFl()).append(Constants.QUOTE);
			vsDtcLine.append(
					vs.getsVsDtc() != null ? "strftime(as.Date(\"" + vs.getsVsDtc() + "\"), \"%Y-%m-%d\")" : "NA");
			vsDyLine.append(vs.getVsDy());
			vsNomDyLine.append(vs.getVsNomDy());
			vsStatLine.append(Constants.QUOTE).append(vs.getVsStat()).append(Constants.QUOTE);

			if (i < vsList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				vsSeqLine.append(Constants.COMMA);
				vsGrpIdLine.append(Constants.COMMA);
				vsRefIdLine.append(Constants.COMMA);
				vsTestCdLine.append(Constants.COMMA);
				vsTestLine.append(Constants.COMMA);
				vsOrresLine.append(Constants.COMMA);
				vsOrresULine.append(Constants.COMMA);
				vsStresCLine.append(Constants.COMMA);
				vsStresNLine.append(Constants.COMMA);
				vsStresULine.append(Constants.COMMA);
				vsBlFlLine.append(Constants.COMMA);
				vsDtcLine.append(Constants.COMMA);
				vsDyLine.append(Constants.COMMA);
				vsNomDyLine.append(Constants.COMMA);
				vsStatLine.append(Constants.COMMA);
			}
		}
	}

}
