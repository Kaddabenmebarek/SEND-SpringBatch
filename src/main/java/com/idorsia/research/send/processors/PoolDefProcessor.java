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
import com.idorsia.research.send.domain.PoolDefinition;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("pooldefProcessor")
@Scope("step")
public class PoolDefProcessor implements ItemProcessor<GenericDomain<PoolDefinition>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder poolIdLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<PoolDefinition> item) throws Exception {
		System.out.println("Calling POOLDEF PROCESSOR");
		StringBuilder rsb = generatePooldefScript("pooldef.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("pooldef", rsb.toString());
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

	private StringBuilder generatePooldefScript(String inputfileName, List<PoolDefinition> data)
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
				if (s.contains("$USUBJID")) {
					s = StringUtils.replace(s, "$USUBJID", usubsIdLine.toString());
				}
				if (s.contains("$POOLID")) {
					s = StringUtils.replace(s, "$POOLID", poolIdLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/pooldef.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "pooldef.xpt";
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

	private void feedLines(List<PoolDefinition> pooldefList) {
		for (int i = 0; i < pooldefList.size(); i++) {
			studyIdLine.append(Constants.QUOTE).append(pooldefList.get(i).getStudyId()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE).append(pooldefList.get(i).getUsubjId()).append(Constants.QUOTE);
			poolIdLine.append(Constants.QUOTE).append(pooldefList.get(i).getPoolId()).append(Constants.QUOTE);

			if (i < pooldefList.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				poolIdLine.append(Constants.COMMA);
			}
		}
	}

}
