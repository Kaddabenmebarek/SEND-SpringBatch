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

import com.idorsia.research.send.domain.Disposition;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("dsProcessor")
@Scope("step")
public class DSProcessor implements ItemProcessor<GenericDomain<Disposition>, Map<String,String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder dsSeqLine = new StringBuilder();
	private StringBuilder dsTermLine = new StringBuilder();
	private StringBuilder dsDecodLine = new StringBuilder();
	private StringBuilder dsUschFlLine = new StringBuilder();
	private StringBuilder dsStdtcLine = new StringBuilder();
	private StringBuilder dsNomDyLine = new StringBuilder();

	@Override
	public Map<String,String> process(GenericDomain<Disposition> item) throws Exception {
		System.out.println("Calling DS PROCESSOR");
		StringBuilder rsb = generateDSScript("ds.R", item.getList());
		Map<String,String> res = new HashMap<String, String>();
		res.put("ds", rsb.toString());
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
	
	private StringBuilder generateDSScript(String inputfileName, List<Disposition> data)
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
				if (s.contains("$DSSEQ")) {
					s = StringUtils.replace(s, "$DSSEQ", dsSeqLine.toString());
				}
				if (s.contains("$DSTERM")) {
					s = StringUtils.replace(s, "$DSTERM", dsTermLine.toString());
				}
				if (s.contains("$DSDECOD")) {
					s = StringUtils.replace(s, "$DSDECOD", dsDecodLine.toString());
				}
				if (s.contains("$DSUSCHFL")) {
					s = StringUtils.replace(s, "$DSUSCHFL", dsUschFlLine.toString());
				}
				if (s.contains("$DSSTDTC")) {
					s = StringUtils.replace(s, "$DSSTDTC", dsStdtcLine.toString());
				}
				if (s.contains("$DSNOMDY")) {
					s = StringUtils.replace(s, "$DSNOMDY", dsNomDyLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/ds.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "ds.xpt";
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

	private void feedLines(List<Disposition> data) {
		for (int i = 0; i < data.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(data.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(data.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(data.get(i).getUsubjId()).append(Constants.QUOTE);
			dsSeqLine.append(i+1);
			dsTermLine.append(Constants.QUOTE).append(data.get(i).getDsTerm()).append(Constants.QUOTE);
			dsDecodLine.append(Constants.QUOTE).append(data.get(i).getDsDecod()).append(Constants.QUOTE);
			dsUschFlLine.append(Constants.QUOTE).append(data.get(i).getDsUschFl()).append(Constants.QUOTE);
			dsNomDyLine.append(Constants.QUOTE).append(data.get(i).getDsNomdy()).append(Constants.QUOTE);
			dsStdtcLine.append(Constants.QUOTE).append(data.get(i).getsDsStdtc()).append(Constants.QUOTE);
			//dsStdtcLine.append("strftime(as.Date(\"data.get(i).getDsStdtc() +"\"), \"%Y-%m-%dT%H:%M:%S\")");
			// dsStdtcLine.append(data.get(i).getDsStdtc() != null ? "strftime(as.POSIXct(\""+ data.get(i).getsDsStdtc() +"\"), format = \"%Y-%m-%dT%H:%M:%S\", tz = \"Europe/Berlin\")" : "NA");

			if (i < data.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				dsSeqLine.append(Constants.COMMA);
				dsTermLine.append(Constants.COMMA);
				dsDecodLine.append(Constants.COMMA);
				dsUschFlLine.append(Constants.COMMA);
				dsStdtcLine.append(Constants.COMMA);
				dsNomDyLine.append(Constants.COMMA);
			}
		}
	}
}
