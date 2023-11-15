package com.idorsia.research.send.writers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.Col;
import com.idorsia.research.send.processors.Constants;

@Component
@Scope("step")
public class TemplatesWriter implements ItemWriter<Map<String, LinkedList<Col>>> {

	private String outputPath;
	private String datasetFolder;
	private String studyId;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
		try {
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			this.outputPath = rb.getString("output_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void write(List<? extends Map<String, LinkedList<Col>>> items) throws Exception {
		
		for (Entry<String, LinkedList<Col>> entry : items.get(0).entrySet()) {
			String rsb = generateScript(entry);
			String generatedScriptPath = writeScript(entry.getKey(), rsb, datasetFolder);
			if (generatedScriptPath != null) {
				System.out.println("Generating SAS file "+entry.getKey()+".xpt");
				SASFileBuilder processBuilderDomain = new SASFileBuilder();
				processBuilderDomain.launchCommand(generatedScriptPath, "R");
			}
		}
	}

	private String writeScript(String domain, String rsb, String datasetFolder) {
		String res = null;
		if (rsb == null || "".equals(rsb.toString())) {
			return null;
		}
		try {
			File scriptsFolder = new File(datasetFolder + File.separator + "scripts");
			boolean scriptsFolderCreated = scriptsFolder.mkdirs();
			if (scriptsFolderCreated || scriptsFolder.exists()) {
				String generatedSciptFile = "/generated-"+domain+".R";
				File newRScript = new File(scriptsFolder.getAbsolutePath() + generatedSciptFile);
				FileWriter fw = new FileWriter(newRScript);
				fw.write(rsb.toString());
				fw.close();
				res = newRScript.getAbsolutePath();
			}
		} catch (IOException iox) {
			iox.printStackTrace();
		}
		if(res.contains("\\")) {
			res = StringUtils.replace(res, "\\", "/");
		}
		return res;
	}

	private String generateScript(Entry<String, LinkedList<Col>> entry) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		File rTemplateScript = ResourceUtils.getFile(outputPath + File.separator + "templates" + File.separator
				+ "R" + File.separator + entry.getKey() + ".R");
		FileReader fr = new FileReader(rTemplateScript);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		try {
			while (s != null) {
				for (Col col : entry.getValue()) {
					//if (s.contains("$" + col.getHeader())) {
					if(s.contains("$") && StringUtils.substringBetween(s, "$", ")").equalsIgnoreCase(col.getHeader())) {
						s = StringUtils.replace(s, "$" + col.getHeader(), getRowsAsString(col.getRows()));
					}
					if (s.contains("$XPT-OUTPUT-PATH")) {
						datasetFolder = outputPath + "/outputs/" + studyId + "/fromTemplates/";
						// String xptOutputPath = PATH + File.separator + "outputs"+ File.separator +"fromTemplates";
						s = StringUtils.replace(s, "$XPT-OUTPUT-PATH", datasetFolder+ entry.getKey() + ".xpt");
					}
				}
				sb.append(s);
				sb.append("\n");
				s = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			br.close();
		}
		return sb.toString();
	}

	private String getRowsAsString(LinkedList<String> rows) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<rows.size(); i++) {
			sb.append(Constants.QUOTE).append(rows.get(i)).append(Constants.QUOTE);
			if(i<rows.size()-1) {
				sb.append(Constants.COMMA);
			}
		}
		String res = StringUtils.replace(sb.toString(), "N/A", "");
		return res;
	}
	
}
