package com.idorsia.research.send.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.idorsia.research.send.domain.EtcdElement;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.SubjectElements;
import com.idorsia.research.spirit.core.dto.StudyDto;

@Component("seProcessor")
@Scope("step")
public class SEProcessor implements ItemProcessor<GenericDomain<SubjectElements>, Map<String, String>> {

	private StudyDto study;
	private String outputPath;
	private StringBuilder studyIdLine = new StringBuilder();
	private StringBuilder domainLine = new StringBuilder();
	private StringBuilder usubsIdLine = new StringBuilder();
	private StringBuilder sestdtcLine = new StringBuilder();
	private StringBuilder seendtcLine = new StringBuilder();
	private StringBuilder etcdLine = new StringBuilder();
	private StringBuilder seSeqLine = new StringBuilder();
	private StepExecution stepExecution;

	@Override
	public Map<String, String> process(GenericDomain<SubjectElements> item) throws Exception {
		System.out.println("Calling SE PROCESSOR");
		StringBuilder rsb = generateSEScript("se.R", item.getList());
		Map<String, String> res = new HashMap<String, String>();
		res.put("se", rsb.toString());
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

	private StringBuilder generateSEScript(String inputfileName, List<SubjectElements> data)
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
				if (s.contains("$SESTDTC")) {
					s = StringUtils.replace(s, "$SESTDTC", sestdtcLine.toString());
				}
				if (s.contains("$SEENDTC")) {
					s = StringUtils.replace(s, "$SEENDTC", seendtcLine.toString());
				}
				if (s.contains("$ETCD")) {
					s = StringUtils.replace(s, "$ETCD", etcdLine.toString());
				}
				if (s.contains("$SESEQ")) {
					s = StringUtils.replace(s, "$SESEQ", seSeqLine.toString());
				}
				if (s.contains("$XPT-OUTPUT-PATH")) {
					String xptOutputPath = "C:/Users/benmeka1/Documents/send/outputs/" + study.getStudyId() + "/se.xpt";
					// String xptOutputPath = PATH + File.separator + "outputs" + File.separator +
					// studyId + File.separator + "se.xpt";
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

	private void feedLines(List<SubjectElements> seList) {
		Map<String, Integer> seSecMap = new HashMap<String, Integer>();
		Map<String, EtcdElement> etcdMap = new HashMap<String, EtcdElement>();
		Set<SubjectElements> seSet = new LinkedHashSet<SubjectElements>();
		for (SubjectElements se : seList) {
			if (se == null)
				continue;
			if (seSecMap.get(se.getEtCd()) == null) {
				int idx = seSecMap.size() + 1;
				seSecMap.put(se.getEtCd(), idx);
				String trtCode = se.getEtCd() != null ? se.getEtCd().equalsIgnoreCase("vehicle") ? "CNTRL" : "TRT" + idx
						: "";
				se.getEtcdElement().setEtcd(trtCode);
				etcdMap.put(se.getEtcdElement().getNamedTreatmentName(), se.getEtcdElement());
			}
			seSet.add(se);
		}
		stepExecution.getJobExecution().getExecutionContext().put("etcdMap", etcdMap);

		List<SubjectElements> data = new ArrayList<SubjectElements>(seSet);
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == null)
				continue;
			studyIdLine.append(Constants.QUOTE).append(data.get(i).getStudyId()).append(Constants.QUOTE);
			domainLine.append(Constants.QUOTE).append(data.get(i).getDomain()).append(Constants.QUOTE);
			usubsIdLine.append(Constants.QUOTE)
					.append(data.get(i).getUsubjId() != null ? data.get(i).getUsubjId() : data.get(i).getSubjId())
					.append(Constants.QUOTE);
			if (data.get(i).getSestDtc() != null) {
				sestdtcLine.append("strftime(as.Date(\"" + data.get(i).getSestDtcAsString() + "\"), \"%Y-%m-%d\")");
			} else {
				sestdtcLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			}
			if (data.get(i).getSeenDtc() != null) {
				seendtcLine.append("strftime(as.Date(\"" + data.get(i).getSeenDtcAsString() + "\"), \"%Y-%m-%d\")");
			} else {
				seendtcLine.append(Constants.QUOTE).append("").append(Constants.QUOTE);
			}
			etcdLine.append(Constants.QUOTE).append(data.get(i).getEtCd()).append(Constants.QUOTE);
			etcdMap.get(data.get(i).getEtcdElement().getNamedTreatmentName()).setSeSeq(seSecMap.get(data.get(i).getEtCd()));
			seSeqLine.append(seSecMap.get(data.get(i).getEtCd())/* + 1*/);
			if (i < data.size() - 1) {
				studyIdLine.append(Constants.COMMA);
				domainLine.append(Constants.COMMA);
				usubsIdLine.append(Constants.COMMA);
				sestdtcLine.append(Constants.COMMA);
				seendtcLine.append(Constants.COMMA);
				etcdLine.append(Constants.COMMA);
				seSeqLine.append(Constants.COMMA);
			}
		}
	}

}
