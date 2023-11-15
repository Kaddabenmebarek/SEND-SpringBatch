package com.idorsia.research.send.writers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.processors.Constants;

@Component
@Scope("step")
public class XPTWriter implements ItemWriter<Map<String,String>>, StepExecutionListener {

	private SASFileBuilder processBuilderDomain = new SASFileBuilder();
	private String studyId;
	private String outputPath;

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
	
	
	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

	@Override
	public void write(List<? extends Map<String, String>> items) throws Exception {
		String domain = items.get(0).keySet().iterator().next();
		String content = items.get(0).values().iterator().next();
		System.out.println("Calling "+domain+" WRITER");
		File rootFolder = new File(outputPath);
		File datasetFolder = new File(
				rootFolder.getAbsolutePath() + File.separator + "outputs" + File.separator + studyId);
		datasetFolder.setReadable(true);
		datasetFolder.setWritable(true);
		datasetFolder.getParentFile().setWritable(true);
		String generatedScriptPath = writeScript(content, domain, datasetFolder);
		if (generatedScriptPath != null) {			
			System.out.println("Generating SAS file "+domain+".xpt");
			processBuilderDomain.launchCommand(generatedScriptPath, "R");
		}
		
	}

	private String writeScript(String content, String domain, File datasetFolder) {
		String res = null;
		try {
			File studyFolder = new File(datasetFolder.getAbsolutePath());
			boolean studyFolderCreated = studyFolder.mkdirs();
			if (studyFolderCreated || studyFolder.exists()) {
				File scriptsFolder = new File(studyFolder.getAbsolutePath() + File.separator + "scripts");
				boolean scriptsFolderCreated = scriptsFolder.mkdirs();
				if (scriptsFolderCreated || scriptsFolder.exists()) {
					String seFileName = "/generated-"+domain+".R";
					File newRScript = new File(scriptsFolder.getAbsolutePath() + seFileName);
					FileWriter fw = new FileWriter(newRScript);
					fw.write(content);
					fw.close();
					res = newRScript.getAbsolutePath();
				}
			}
		} catch (IOException iox) {
			iox.printStackTrace();
		}
		if (res.contains("\\")) {
			res = StringUtils.replace(res, "\\", "/");
		}
		return res;
	}

}
