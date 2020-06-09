package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.CompanyAddress;

public class JsonToCompanyAddressConverter implements Converter<String, CompanyAddress> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CompanyAddress convert(String source) {
		try {
			return jsonMapper.readValue(source, CompanyAddress.class);
		} catch (IOException e) {
		}
		return null;
	}
}
