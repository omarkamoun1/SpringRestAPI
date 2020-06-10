package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.proxy.ProxyParameter;

public class JsonToProxyParameterConverter implements Converter<String, ProxyParameter> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ProxyParameter convert(String source) {
		try {
			return jsonMapper.readValue(source, ProxyParameter.class);
		} catch (IOException e) {
		}
		return null;
	}
}
