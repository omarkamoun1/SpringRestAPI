package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.UpdatePayRecordDef;

public class JsonToUpdatePayRecordDefConverter implements Converter<String, UpdatePayRecordDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdatePayRecordDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdatePayRecordDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
