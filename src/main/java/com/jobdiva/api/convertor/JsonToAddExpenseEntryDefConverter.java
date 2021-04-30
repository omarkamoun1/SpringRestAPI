package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.AddExpenseEntryDef;

public class JsonToAddExpenseEntryDefConverter implements Converter<String, AddExpenseEntryDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddExpenseEntryDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddExpenseEntryDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
