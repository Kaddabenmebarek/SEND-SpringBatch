package com.idorsia.research.send.writers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.TumorFindings;
import com.idorsia.research.send.processors.Constants;

@Component("tfDataWriter")
@Scope("step")
public class TFDataWriter implements ItemWriter<TumorFindings> {

	private SASFileBuilder processBuilderDomain = new SASFileBuilder();
    private String studyId;
    private String outputPath;
    private StringBuilder studyIdLine = new StringBuilder();
    private StringBuilder domainLine = new StringBuilder();
    private StringBuilder usubsIdLine = new StringBuilder();
    private StringBuilder tfSeqLine = new StringBuilder();
    private StringBuilder tfSpIddLine = new StringBuilder();
    private StringBuilder tfTestCdLine = new StringBuilder();
    private StringBuilder tfTestLine = new StringBuilder();
    private StringBuilder tfOrresULine = new StringBuilder();
    private StringBuilder tfStresCLine = new StringBuilder();
    private StringBuilder tfResCatLine = new StringBuilder();
    private StringBuilder tfSpecLine = new StringBuilder();
    private StringBuilder tfDthRelLine = new StringBuilder();
    private StringBuilder tfDtcLine = new StringBuilder();
    private StringBuilder tfDyLine = new StringBuilder();
    private StringBuilder tfDetectLine = new StringBuilder();
    
    @AfterStep
    public void afterStep(StepExecution stepExecution) throws IOException {
    }
 
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
		try {
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			outputPath = rb.getString("output_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Calling tf beforeStep");
    }
 
	@Override
	@SuppressWarnings("unchecked")
    public void write(List<? extends TumorFindings> items) throws Exception {
		File rootFolder = new File(outputPath);
		File datasetFolder = new File(rootFolder.getAbsolutePath() + File.separator + "outputs" + File.separator + studyId);
		datasetFolder.setReadable(true);
		datasetFolder.setWritable(true);
		datasetFolder.getParentFile().setWritable(true);
		
		StringBuilder rsb = generateFWScript("tf.R", (List<TumorFindings>) items);    	
		String generatedScriptPath = writeScript(rsb, datasetFolder);    	
		if (generatedScriptPath != null) {
			System.out.println("Generating SAS file tf.xpt");
			processBuilderDomain.launchCommand(generatedScriptPath, "R");
		}
    }
    
    
    private String writeScript(StringBuilder rsb, File datasetFolder) {
		String res = null;
		if (rsb == null || "".equals(rsb.toString())) {
			return null;
		}
		try {
			File studyFolder = new File(datasetFolder.getAbsolutePath());
			boolean studyFolderCreated = studyFolder.mkdirs();
			if (studyFolderCreated || studyFolder.exists()) {
				File scriptsFolder = new File(studyFolder.getAbsolutePath() + File.separator + "scripts");
				boolean scriptsFolderCreated = scriptsFolder.mkdirs();
				if (scriptsFolderCreated || scriptsFolder.exists()) {
					String seFileName = "/generated-tf.R";
					File newRScript = new File(scriptsFolder.getAbsolutePath() + seFileName);
					FileWriter fw = new FileWriter(newRScript);
					fw.write(rsb.toString());
					fw.close();
					res = newRScript.getAbsolutePath();
				}
			}
		} catch (IOException iox) {
			iox.printStackTrace();
		}
		if(res.contains("\\")) {
			res = StringUtils.replace(res, "\\", "/");
		}
		return res;
	}

	private StringBuilder generateFWScript(String inputfileName, List<TumorFindings> data) throws FileNotFoundException, IOException {
    	feedLines(data);
		File sourceFile = ResourceUtils.getFile(outputPath + File.separator + "templates"  + File.separator + "R"  + File.separator + inputfileName);
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
				if (s.contains("$TFSEQ")) {
					s = StringUtils.replace(s, "$TFSEQ", tfSeqLine.toString());
				}
				if (s.contains("$TFSPID")){
					s = StringUtils.replace(s, "$TFSPID", tfSpIddLine.toString());
				}
				if (s.contains("$CDTFTEST")){
					s = StringUtils.replace(s, "$CDTFTEST", tfTestCdLine.toString());
				}
				if (s.contains("$TFTEST")) {
					s = StringUtils.replace(s, "$TFTEST", tfTestLine.toString());
				}
				if (s.contains("$TFORRES")) {
					s = StringUtils.replace(s, "$TFORRES", tfOrresULine.toString());
				}
				if (s.contains("$TFSTRESC")) {
					s = StringUtils.replace(s, "$TFSTRESC", tfStresCLine.toString());
				}
				if (s.contains("$TFRESCAT")) {
					s = StringUtils.replace(s, "$TFRESCAT", tfResCatLine.toString());
				}
				if (s.contains("$TFSPEC")) {
					s = StringUtils.replace(s, "$TFSPEC", tfSpecLine.toString());
				}
				if (s.contains("$TFDTHREL")) {
					s = StringUtils.replace(s, "$TFDTHREL", tfDthRelLine.toString());
				}
				if (s.contains("$TFDTC")) {
					s = StringUtils.replace(s, "$TFDTC", tfDtcLine.toString());
				}
				if (s.contains("$TFDY")) {
					s = StringUtils.replace(s, "$TFDY", tfDyLine.toString());
				}
				if (s.contains("$TFDETECT")) {
					s = StringUtils.replace(s, "$TFDETECT", tfDetectLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/"+studyId+"/tf.xpt";
					//String xptOutputPath = PATH + File.separator + "outputs"  + File.separator + studyId + File.separator + "tf.xpt";
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

	private void feedLines(List<TumorFindings> vsList) {
		for(int i=0; i<vsList.size(); i++) {
			TumorFindings vs = vsList.get(i);
			studyIdLine.append(Constants.QUOTE).append(vs.getStudyId()).append(Constants.QUOTE);
    		domainLine.append(Constants.QUOTE).append(vs.getDomain()).append(Constants.QUOTE);
    		usubsIdLine.append(Constants.QUOTE).append(vs.getStudyId() + "-" + vs.getUsubjId()).append(Constants.QUOTE);
    		//TODO 
    		
    		if(i<vsList.size()-1) {
    			studyIdLine.append(Constants.COMMA);
    			domainLine.append(Constants.COMMA);
    			usubsIdLine.append(Constants.COMMA);
    			tfSeqLine.append(Constants.COMMA);
    			tfSpIddLine.append(Constants.COMMA);
    			tfTestCdLine.append(Constants.COMMA);
    			tfTestLine.append(Constants.COMMA);
    			tfOrresULine.append(Constants.COMMA);
    			tfStresCLine.append(Constants.COMMA);
    			tfResCatLine.append(Constants.COMMA);
    			tfSpecLine.append(Constants.COMMA);
    			tfDthRelLine.append(Constants.COMMA);
    			tfDtcLine.append(Constants.COMMA);
    			tfDyLine.append(Constants.COMMA);
    			tfDetectLine.append(Constants.COMMA);
    		}
    	}
    }


}
