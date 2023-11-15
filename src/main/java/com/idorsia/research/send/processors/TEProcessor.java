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
import com.idorsia.research.send.domain.TrialElements;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("teProcessor")
@Scope("step")
public class TEProcessor implements ItemProcessor<GenericDomain<TrialElements>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
    private StringBuilder studyIdLine = new StringBuilder();
    private StringBuilder domainLine = new StringBuilder();
    private StringBuilder etcdLine = new StringBuilder();
    private StringBuilder elementLine = new StringBuilder();
    private StringBuilder teStrlLine = new StringBuilder();
    private StringBuilder teEnrlLine = new StringBuilder();
    private StringBuilder teDurLine = new StringBuilder();

	@Override
	public Map<String, String> process(GenericDomain<TrialElements> item) throws Exception {
		System.out.println("Calling TE PROCESSOR");
		StringBuilder rsb = generateTEScript("te.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("te", rsb.toString());
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

	private StringBuilder generateTEScript(String inputfileName, List<TrialElements> data)
			throws FileNotFoundException, IOException {
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
				if (s.contains("$ETCD")) {
					s = StringUtils.replace(s, "$ETCD", etcdLine.toString());
				}
				if (s.contains("$ELEMENT")) {
					s = StringUtils.replace(s, "$ELEMENT", elementLine.toString());
				}
				if (s.contains("$TESTRL")){
					s = StringUtils.replace(s, "$TESTRL", teStrlLine.toString());
				}
				if (s.contains("$TEENRL")){
					s = StringUtils.replace(s, "$TEENRL", teEnrlLine.toString());
				}
				if (s.contains("$TEDUR")) {
					s = StringUtils.replace(s, "$TEDUR", teDurLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/"+study.getStudyId()+"/te.xpt";
					//String xptOutputPath = PATH + File.separator + "outputs"  + File.separator + studyId + File.separator + "te.xpt";
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

	private void feedLines(List<TrialElements> data) {
		for(int i = 0; i < data.size(); i++) {
			TrialElements te = data.get(i);
			studyIdLine.append(Constants.QUOTE).append(te.getStudyId()).append(Constants.QUOTE);
    		domainLine.append(Constants.QUOTE).append(te.getDomain()).append(Constants.QUOTE);
    		etcdLine.append(Constants.QUOTE).append(te.geteTcd()).append(Constants.QUOTE);
		    elementLine.append(Constants.QUOTE).append(te.getElement()).append(Constants.QUOTE);
		    teStrlLine.append(Constants.QUOTE).append(te.getTesTrl() != null ? te.getTesTrl() : "").append(Constants.QUOTE);
		    teEnrlLine.append(Constants.QUOTE).append(te.getTeeNrl() != null ? te.getTeeNrl() : "").append(Constants.QUOTE);
		    teDurLine.append(Constants.QUOTE).append(te.getTeDur()).append(Constants.QUOTE);
    		
    		if(i<data.size()-1) {
    			studyIdLine.append(Constants.COMMA);
    			domainLine.append(Constants.COMMA);
    		    etcdLine.append(Constants.COMMA);
    		    elementLine.append(Constants.COMMA);
    		    teStrlLine.append(Constants.COMMA);
    		    teEnrlLine.append(Constants.COMMA);
    		    teDurLine.append(Constants.COMMA);
    		}
    	}
    }

}
