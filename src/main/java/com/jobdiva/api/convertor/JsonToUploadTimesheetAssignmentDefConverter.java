package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.UploadTimesheetAssignmentDef;

public class JsonToUploadTimesheetAssignmentDefConverter implements Converter<String, UploadTimesheetAssignmentDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UploadTimesheetAssignmentDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UploadTimesheetAssignmentDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
