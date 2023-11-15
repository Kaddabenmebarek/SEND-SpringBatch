package com.idorsia.research.send;

import java.util.ArrayList;
import java.util.List;

import com.idorsia.research.send.processors.DataUtils;

public class TestStringScore {

	public static void main(String[] args) {
		List<String> list = SendService.getTerminologyByCode("2023-03-31", "DSDECOD");
		List<String> tests = new ArrayList<String>();
		populateTest(tests);
		for(String test : tests) {			
			DataUtils.getTerminologyCandidate(test, list);
		}
	}


	private static void populateTest(List<String> tests) {
		tests.add("terminal");
		tests.add("moribund sacrifice");
		tests.add("found dead");
		tests.add("accidental");
	}
	
	
}
