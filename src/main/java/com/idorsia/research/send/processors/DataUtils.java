package com.idorsia.research.send.processors;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;

import com.idorsia.research.send.SendService;

public class DataUtils {
	
    public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }
    
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTimeToConvert) {
    	return Date.from(localDateTimeToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    
	public static Timestamp getDate(Duration phaseDuration, Duration stratification, Date assignmentStageStartDate, Date animalDeath) {
		if (phaseDuration == null || assignmentStageStartDate == null)
			return null;
		Duration offset = stratification;
		
		long offsetEpochMil = offset == null ? 0 : offset.toMillis();
		long phaseDurationEpochMil = phaseDuration.toMillis();
		Instant inst = toInstant(assignmentStageStartDate);
		long assignmentStageStartDateEpochMil = inst.toEpochMilli();
		
		Date from = Date.from(Instant.ofEpochMilli(assignmentStageStartDateEpochMil + offsetEpochMil + phaseDurationEpochMil));
		
		if (animalDeath!=null && animalDeath.before(from))
			return null;
		
		return new Timestamp(from.getTime());
	}
	
	public static Integer getDayBetween(Timestamp dtc, Date startDate) {
		long bwDtc = dtc != null ? dtc.getTime() : 0;
		long stageStartDate = startDate != null ? startDate.getTime() : 0;
		Long dy = TimeUnit.MILLISECONDS.toDays(bwDtc - stageStartDate);    	    		
		return dy.intValue();
	}
    
	public static String getTerminologyCandidate(String s, List<String> tList) {
		String match = null;
		ResourceBundle rb = null;
		try {
			FileInputStream fi = new FileInputStream("src/main/resources/spiritCTDictionary.properties");
			rb = new PropertyResourceBundle(fi);
			String key = s.replace(" ", "_").replace(":", "");
			if (rb.getString(key) != null) {
				match = rb.getString(key);
				//System.out.println("From dico for " +s+ " = "+match);
				return match;
			}
		} catch (MissingResourceException | IOException e) {}

		Map<Double, Set<String>> resultMap = new TreeMap<Double, Set<String>>();
		try {
			for (String t : tList) {
				testStringDistance(s, t, resultMap);
			}
			Entry<Double, Set<String>> lastEntry = ((TreeMap<Double, Set<String>>) resultMap).lastEntry();
			if (lastEntry.getValue().size() == 1) {
				match = lastEntry.getValue().iterator().next();
			} else {
				for (String target : lastEntry.getValue()) {
					if (target.toUpperCase().contains(s.toUpperCase())
							|| s.toUpperCase().contains(target.toUpperCase())) {
						match = target;
					}
				}
			}
			String result = match != null ? "Potential candidate for " + s + " = " + match : "Nothing found for " + s;
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return match;
	}

	public static void testStringDistance(String s, String t, Map<Double, Set<String>> resultMap) throws Exception {
		String s1;
		if(s.contains("/")){
			s1=s.split("/")[0];
		}
		//s = removePrefix(s);
		s1 = StringUtils.replace(s, ",", "").replace("+", "").replace("&", "").replace("(", "").replace(")", "").toUpperCase();
	 	String t1 = StringUtils.replace(t, ",", "").toUpperCase();
	 	double distanceJaroWinkler;
	 	if(s1.contains(t1) || t1.contains(s1)) {
	 		distanceJaroWinkler = 1;
	 	}else {	 		
	 		distanceJaroWinkler = StringUtils.getJaroWinklerDistance(s1, t1);
	 	}
	    //double distanceLevenshtein = StringUtils.getLevenshteinDistance(s1.toUpperCase(), t1.toUpperCase());
	    //System.out.println("Between " +s+ " and " +t);
	    //System.out.println("Score: "+distanceJaroWinkler);
	    if(resultMap.get(distanceJaroWinkler) != null) {
	    	resultMap.get(distanceJaroWinkler).add(t);
	    }else {
	    	Set<String> candidates = new HashSet<String>();
	    	candidates.add(t);
	    	resultMap.put(distanceJaroWinkler,candidates);
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getCT(StepExecution stepExecution ,String ctversion, String code) {
		List<String> ctList;
		if(stepExecution.getJobExecution().getExecutionContext().get(code)==null) {
			ctList = SendService.getTerminologyByCode(ctversion, code);
			stepExecution.getJobExecution().getExecutionContext().put(code,ctList);
		}else {
			ctList = (List<String>) stepExecution.getJobExecution().getExecutionContext().get(code);
		}
		return ctList;
	}

	public static void clearContext(StepExecution stepExecution, Set<String> keys) {
		for(String key : keys) {    		
    		stepExecution.getJobExecution().getExecutionContext().remove(key);
    	}
	}
	
	public static Long durationToLong(Duration source) {
		if(source != null) {
			return source.toNanos();
		}
		return null;
	}

	public static Duration longToDuration(Long source) {
		if(source != null) {
			return Duration.ofNanos(source);
		}
		return null;
	}
	
	public static Instant toInstant(Date date) {
	    return Instant.ofEpochMilli(date.getTime());
	}

	public static Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static long getPhase(Date date, Map<Date, Duration> datePhaseRefMap) {//FIXME WRONG NEED TO PROVIDE STAGE AS A CONTEXT
		Long refPhase = null;
		Date refDate = null;
		Long res;
		for(Entry<Date, Duration> entry : datePhaseRefMap.entrySet()) {
			refPhase = entry.getValue().toDays();
			refDate = entry.getKey();
			break;
		}
		if(refDate.after(date)) {
			long diff = refDate.getTime() - date.getTime();
		    long difffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		    res = refPhase - difffDays;
		}else {
			long diff = date.getTime() - refDate.getTime();
		    long difffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		    res = refPhase + difffDays;
		}
		return res;
	}
	
	
}
