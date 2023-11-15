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
import com.idorsia.research.send.domain.TrialArms;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("taProcessor")
@Scope("step")
public class TAProcessor implements ItemProcessor<GenericDomain<TrialArms>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
    private StringBuilder domainLine = new StringBuilder();
    private StringBuilder amrcdLine = new StringBuilder();
    private StringBuilder amrLine = new StringBuilder();
    private StringBuilder taEtordLine = new StringBuilder();
    private StringBuilder etcdLine = new StringBuilder();
    private StringBuilder elementLine = new StringBuilder();
    private StringBuilder taBranchLine = new StringBuilder();
    private StringBuilder epochLine = new StringBuilder();
    private StepExecution stepExecution;

	@Override
	public Map<String, String> process(GenericDomain<TrialArms> item) throws Exception {
		System.out.println("Calling TA PROCESSOR");
		StringBuilder rsb = generateTAScript("ta.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("ta", rsb.toString());
		return res;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		try {
			FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			outputPath = rb.getString("output_root_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder generateTAScript(String inputfileName, List<TrialArms> data)
			throws FileNotFoundException, IOException {
    	feedLines(data);
		File sourceFile = ResourceUtils.getFile(outputPath + File.separator + "templates"  + File.separator + "R"  + File.separator + inputfileName);
		StringBuilder sb = new StringBuilder();
		FileReader fr = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		try { 
			while (s != null) {
				//STUDYID,DOMAIN,ARMCD,ARM,TAETORD,ETCD,ELEMENT,TABRANCH,EPOCH
				if (s.contains("$STUDYID")) {
					s = StringUtils.replace(s, "$STUDYID", studyIdLine.toString());
				}
				if (s.contains("$DOMAIN")) {
					s = StringUtils.replace(s, "$DOMAIN", domainLine.toString());
				}
				if (s.contains("$CDARM")) {
					s = StringUtils.replace(s, "$CDARM", amrcdLine.toString());
				}
				if (s.contains("$ARM")) {
					s = StringUtils.replace(s, "$ARM", amrLine.toString());
				}
				if (s.contains("$TAETORD")) {
					s = StringUtils.replace(s, "$TAETORD", taEtordLine.toString());
				}				
				if (s.contains("$ETCD")) {
					s = StringUtils.replace(s, "$ETCD", etcdLine.toString());
				}
				if (s.contains("$ELEMENT")) {
					s = StringUtils.replace(s, "$ELEMENT", elementLine.toString());
				}
				if (s.contains("$TABRANCH")){
					s = StringUtils.replace(s, "$TABRANCH", taBranchLine.toString());
				}
				if (s.contains("$EPOCH")){
					s = StringUtils.replace(s, "$EPOCH", epochLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/"+study.getStudyId()+"/ta.xpt";
					//String xptOutputPath = PATH + File.separator + "outputs"  + File.separator + studyId + File.separator + "ta.xpt";
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

	private void feedLines(List<TrialArms> taList) {
		Map<String, String> armcdMap = new HashMap<String, String>();
		for(int j=0;j<taList.size();j++) {
			TrialArms ta = taList.get(j);
			studyIdLine.append(Constants.QUOTE).append(ta.getStudyId()).append(Constants.QUOTE);
    		domainLine.append(Constants.QUOTE).append(ta.getDomain()).append(Constants.QUOTE);
    		amrcdLine.append(Constants.QUOTE).append(ta.getArmCd()).append(Constants.QUOTE);
		    amrLine.append(Constants.QUOTE).append(ta.getArm()).append(Constants.QUOTE);
		    armcdMap.put(ta.getArm(), ta.getArmCd());
		    taEtordLine.append(Constants.QUOTE).append(ta.getTaEtOrd()).append(Constants.QUOTE);
    		etcdLine.append(Constants.QUOTE).append(ta.getEtCd()).append(Constants.QUOTE);
		    elementLine.append(Constants.QUOTE).append(ta.getElement()).append(Constants.QUOTE);
		    taBranchLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
		    epochLine.append(Constants.QUOTE).append(ta.getEpoch()).append(Constants.QUOTE);
    		
    		if(j<taList.size()-1) {
    			studyIdLine.append(Constants.COMMA);
    			domainLine.append(Constants.COMMA);
    			amrcdLine.append(Constants.COMMA);
    		    amrLine.append(Constants.COMMA);
    		    taEtordLine.append(Constants.COMMA);    		    
    		    etcdLine.append(Constants.COMMA);
    		    elementLine.append(Constants.COMMA);
    		    taBranchLine.append(Constants.COMMA);
    		    epochLine.append(Constants.COMMA);
    		}
    	}
		stepExecution.getJobExecution().getExecutionContext().put("armcdMap", armcdMap);
    }

}
