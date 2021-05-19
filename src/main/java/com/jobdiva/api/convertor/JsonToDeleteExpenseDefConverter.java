package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.DeleteExpenseDef;

public class JsonToDeleteExpenseDefConverter implements Converter<String, DeleteExpenseDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public DeleteExpenseDef convert(String source) {
		try {
			return jsonMapper.readValue(source, DeleteExpenseDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
