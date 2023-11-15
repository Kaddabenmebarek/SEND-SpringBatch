package com.idorsia.research.send.processors;


import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.batch.item.ItemProcessor;

import com.idorsia.research.send.domain.TumorFindings;

public class DatabaseTFProcessor implements ItemProcessor<TumorFindings,TumorFindings> {

	@Override
	public TumorFindings process(TumorFindings item) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
		if(item.getTfDtc() != null) {
			item.setsTfDtc(sdf.format(item.getTfDtc()));
		}
		
		//TODO
		
		
		
		return item;
	}

}
