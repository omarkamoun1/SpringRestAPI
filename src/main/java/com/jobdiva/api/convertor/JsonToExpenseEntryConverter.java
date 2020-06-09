package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.ExpenseEntry;

public class JsonToExpenseEntryConverter implements Converter<String, ExpenseEntry> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ExpenseEntry convert(String source) {
		try {
			return jsonMapper.readValue(source, ExpenseEntry.class);
		} catch (IOException e) {
		}
		return null;
	}
}
