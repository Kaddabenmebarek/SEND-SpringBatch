package com.idorsia.research.send.processors;



import org.springframework.batch.item.ItemProcessor;

import com.idorsia.research.send.domain.SendTerminology;


public class CTerminologyProcessor implements ItemProcessor<SendTerminology,SendTerminology> {

	@Override
	public SendTerminology process(SendTerminology item) throws Exception {

		
		//TODO CT validation		
		
		return item;
	}

}
