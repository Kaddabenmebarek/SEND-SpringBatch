package com.idorsia.research.send.writers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.Demographics;
import com.idorsia.research.send.processors.Constants;

@Component("dMWriter")
@Scope("step")
public class DMWriter implements ItemWriter<Demographics> {

    private SASFileBuilder processBuilderDomain = new SASFileBuilder();
    private String studyId;
    private static String outputPath;
    private StringBuilder studyIdLine = new StringBuilder();
    private StringBuilder domainLine = new StringBuilder();
    private StringBuilder usubsIdLine = new StringBuilder();
    private StringBuilder subjIdLine = new StringBuilder();
    private StringBuilder rfstdtcLine = new StringBuilder();
    private StringBuilder rfendtcLine = new StringBuilder();
    private StringBuilder ageLine = new StringBuilder();
    private StringBuilder ageULine = new StringBuilder();
    private StringBuilder sexLine = new StringBuilder();
    private StringBuilder speciesLine = new StringBuilder();
    private StringBuilder strainLine = new StringBuilder();
    private StringBuilder armLine = new StringBuilder();
    private StringBuilder armCDLine = new StringBuilder();
    private StringBuilder setCDLine = new StringBuilder();
	
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
        System.out.println("Calling DM beforeStep");
    }
 
	@Override
	@SuppressWarnings("unchecked")
    public void write(List<? extends Demographics> items) throws Exception {
		File rootFolder = new File(outputPath);
		File datasetFolder = new File(rootFolder.getAbsolutePath() + File.separator + "outputs" + File.separator + studyId);
		datasetFolder.setReadable(true);
		datasetFolder.setWritable(true);
		datasetFolder.getParentFile().setWritable(true);
		
		StringBuilder rsb = generateDMScript("dm.R", (List<Demographics>) items);    	
		String generatedScriptPath = writeScript(rsb, datasetFolder);    	
		if (generatedScriptPath != null) {
			System.out.println("Generating SAS file dm.xpt");
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
					String dmFileName = "/generated-dm.R";
					File newRScript = new File(scriptsFolder.getAbsolutePath() + dmFileName);
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

	private StringBuilder generateDMScript(String inputfileName, List<Demographics> data)
			throws FileNotFoundException, IOException {
    	feedLines(data);
		//File sourceFile = ResourceUtils.getFile("classpath:" + inputfileName);
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
				if (s.contains("$SUBJID")) {
					s = StringUtils.replace(s, "$SUBJID", subjIdLine.toString());
				}
				if (s.contains("$RFSTDTC")) {
					s = StringUtils.replace(s, "$RFSTDTC", rfstdtcLine.toString());
				}
				if (s.contains("$RFENDTC")) {
					s = StringUtils.replace(s, "$RFENDTC", rfendtcLine.toString());
				}
				if (s.contains("$AGE")) {
					s = StringUtils.replace(s, "$AGE", ageLine.toString());
				}				
				if (s.contains("$UAGE")) {
					s = StringUtils.replace(s, "$UAGE", ageULine.toString());
				}
				if (s.contains("$SEX")) {
					s = StringUtils.replace(s, "$SEX", sexLine.toString());
				}
				if (s.contains("$SPECIES")) {
					s = StringUtils.replace(s, "$SPECIES", speciesLine.toString());
				}
				if (s.contains("$STRAIN")) {
					s = StringUtils.replace(s, "$STRAIN", strainLine.toString());
				}
				if (s.contains("$ARM")) {
					s = StringUtils.replace(s, "$ARM", armLine.toString());
				}
				if (s.contains("$CDARM")) {
					s = StringUtils.replace(s, "$CDARM", armCDLine.toString());
				}
				if (s.contains("$SETCD")) {
					s = StringUtils.replace(s, "$SETCD", setCDLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/"+studyId+"/dm.xpt";
					//String xptOutputPath = PATH + File.separator + "outputs"  + File.separator + studyId + File.separator + "dm.xpt";
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

	private void feedLines(List<Demographics> dmList) {
		Map<String, Integer> setCdMap = new HashMap<String,Integer>();
		Set<Demographics> data = removeDupplicates(dmList);
		for(Demographics dm :data) {
			if(setCdMap.get(dm.getArm()) == null) {
				setCdMap.put(dm.getArm(), setCdMap.size() + 1);
			}	
		}
		List<Demographics> dms = new ArrayList<Demographics>(data);
		for(int i=0; i<dms.size(); i++) {
			Demographics dm = dms.get(i);
			if(dm.getArm() == null) continue;
    		studyIdLine.append(Constants.QUOTE).append(dm.getStudyId()).append(Constants.QUOTE);
    		domainLine.append(Constants.QUOTE).append(dm.getDomain()).append(Constants.QUOTE);
    		usubsIdLine.append(Constants.QUOTE).append(dm.getStudyId() + "-" + dm.getSubjId()).append(Constants.QUOTE);
    		subjIdLine.append(Constants.QUOTE).append(dm.getSubjId() != null ? dm.getSubjId() : "").append(Constants.QUOTE);
    		rfstdtcLine.append(dm.getRfStDtc() != null ? "strftime(as.Date(\""+ dm.getRfStDtc() +"\"), \"%Y-%m-%d\")" : "NA");
    		rfendtcLine.append(dm.getRfEnDtc() != null ? "strftime(as.Date(\""+ dm.getRfEnDtc() +"\"), \"%Y-%m-%d\")" : "NA");
    		Long age = retrieveAge(dm);
    		ageLine.append(age != null ? age : "NA"); //age and agetxt never combined!
    		ageULine.append(Constants.QUOTE).append(Constants.AGE_UNIT_WEEKS).append(Constants.QUOTE);
    		sexLine.append(Constants.QUOTE).append(dm.getSex() != null ? dm.getSex() : "").append(Constants.QUOTE);
    		speciesLine.append(Constants.QUOTE).append(dm.getSpecies() != null ? dm.getSpecies() : "NA").append(Constants.QUOTE);
    		strainLine.append(Constants.QUOTE).append(dm.getStrain() != null ? dm.getStrain() : "NA").append(Constants.QUOTE);
    		armLine.append(Constants.QUOTE).append(dm.getArm()).append(Constants.QUOTE);
    		armCDLine.append(dm.getArmCd() != null ? dm.getArmCd() : "");
    		setCDLine.append(setCdMap.get(dm.getArm()));
    		if(i<dms.size()-1) {
    			studyIdLine.append(Constants.COMMA);
    			domainLine.append(Constants.COMMA);
    			usubsIdLine.append(Constants.COMMA);
    			subjIdLine.append(Constants.COMMA);
    			rfstdtcLine.append(Constants.COMMA);
    			rfendtcLine.append(Constants.COMMA);
    			ageLine.append(Constants.COMMA);
    			ageULine.append(Constants.COMMA);
    			sexLine.append(Constants.COMMA);
    			speciesLine.append(Constants.COMMA);
    			strainLine.append(Constants.COMMA);
    			armLine.append(Constants.COMMA);
    			armCDLine.append(Constants.COMMA);
    			setCDLine.append(Constants.COMMA);
    		}
    	}
	}

	private Set<Demographics> removeDupplicates(List<Demographics> dmList) {
		Set<Demographics> data = new LinkedHashSet<Demographics>();
		for(Demographics dm : dmList) {
			data.add(dm);
		}
		return data;
	}

	//TODO SET AVERAGE INSTEAD OF EXACT AGE
	private Long retrieveAge(Demographics dm) {
		Long age = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.SD_FORMAT);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(Constants.SD_FORMAT2);
		LocalDate studyStartDate = LocalDate.parse(dm.getRfStDtc(), formatter);
		if(dm.getBrthDtc() != null) {
			LocalDate brthDtc = LocalDate.parse(dm.getBrthDtc(), formatter2);
			//Weeks weeks = Weeks.weeksBetween(brthDtc, studyStartDate);
			age=brthDtc.until(studyStartDate, ChronoUnit.WEEKS);
		}else if(dm.getDeliveryDtc() != null) {
			LocalDate deliveryDtc = LocalDate.parse(dm.getDeliveryDtc(), DateTimeFormatter.ofPattern(Constants.SD_FORMAT2));
			age=deliveryDtc.until(studyStartDate, ChronoUnit.WEEKS);
		}
		return age;
	}

}
