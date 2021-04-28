package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.ApproveExpenseEntryDef;

public class JsonToApproveExpenseEntryDefConverter implements Converter<String, ApproveExpenseEntryDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ApproveExpenseEntryDef convert(String source) {
		try {
			return jsonMapper.readValue(source, ApproveExpenseEntryDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
