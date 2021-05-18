package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Timesheet;

public class JsonToTimesheetConverter implements Converter<String, Timesheet> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Timesheet convert(String source) {
		try {
			return jsonMapper.readValue(source, Timesheet.class);
		} catch (IOException e) {
		}
		return null;
	}
}
