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
import com.idorsia.research.send.domain.TrialSets;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("txProcessor")
@Scope("step")
public class TXProcessor implements ItemProcessor<GenericDomain<TrialSets>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
    private StringBuilder domainLine = new StringBuilder();
    private StringBuilder setcdLine = new StringBuilder();
    private StringBuilder setLine = new StringBuilder();
    private StringBuilder txSeqLine = new StringBuilder();
    private StringBuilder txParamCdLine = new StringBuilder();
    private StringBuilder txParamLine = new StringBuilder();
    private StringBuilder txValLine = new StringBuilder();
    private StepExecution stepExecution;

	@Override
	public Map<String, String> process(GenericDomain<TrialSets> item) throws Exception {
		System.out.println("Calling TX PROCESSOR");
		StringBuilder rsb = generateTxScript("tx.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("tx", rsb.toString());
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

	private StringBuilder generateTxScript(String inputfileName, List<TrialSets> data)
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
				if (s.contains("$CDSET")) {
					s = StringUtils.replace(s, "$CDSET", setcdLine.toString());
				}
				if (s.contains("SET")) {
					s = StringUtils.replace(s, "$SET", setLine.toString());
				}
				if (s.contains("$TXSEQ")) {
					s = StringUtils.replace(s, "$TXSEQ", txSeqLine.toString());
				}				
				if (s.contains("$TXPARMCD")) {
					s = StringUtils.replace(s, "$TXPARMCD", txParamCdLine.toString());
				}
				if (s.contains("$TXPARM")) {
					s = StringUtils.replace(s, "$TXPARM", txParamLine.toString());
				}
				if (s.contains("$TXVAL")){
					s = StringUtils.replace(s, "$TXVAL", txValLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/"+study.getStudyId()+"/tx.xpt";
					//String xptOutputPath = PATH + File.separator + "outputs"  + File.separator + studyId + File.separator + "tx.xpt";
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

	private void feedLines(List<TrialSets> txList) {
		for(int j=0;j<txList.size();j++) {
			TrialSets tx = txList.get(j);
			studyIdLine.append(Constants.QUOTE).append(tx.getStudyId()).append(Constants.QUOTE);
    		domainLine.append(Constants.QUOTE).append(tx.getDomain()).append(Constants.QUOTE);
    		setcdLine.append(tx.getSetCd());
		    setLine.append(Constants.QUOTE).append(tx.getSet()).append(Constants.QUOTE);
		    txSeqLine.append(tx.getTxSeq());
		    txParamCdLine.append(Constants.QUOTE).append(tx.getTxParmCd()).append(Constants.QUOTE);
		    txParamLine.append(Constants.QUOTE).append(tx.getTxParm()).append(Constants.QUOTE);
		    txValLine.append(Constants.QUOTE).append(tx.getTxVal()).append(Constants.QUOTE);
    		
    		if(j<txList.size()-1) {
    			studyIdLine.append(Constants.COMMA);
    			domainLine.append(Constants.COMMA);
    			setcdLine.append(Constants.COMMA);
    		    setLine.append(Constants.COMMA);
    		    txSeqLine.append(Constants.COMMA);
    		    txParamCdLine.append(Constants.COMMA);
    		    txParamLine.append(Constants.COMMA);
    		    txValLine.append(Constants.COMMA);
    		}
    	}
    }

}
