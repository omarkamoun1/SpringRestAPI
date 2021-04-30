package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.CreateBillingRecordDef;

public class JsonToCreateBillingRecordDefConverter implements Converter<String, CreateBillingRecordDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateBillingRecordDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateBillingRecordDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
