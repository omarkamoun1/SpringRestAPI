package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.UpdatePayrollProfileDef;

public class JsonToUpdatePayrollProfileDefConverter implements Converter<String, UpdatePayrollProfileDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdatePayrollProfileDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdatePayrollProfileDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
