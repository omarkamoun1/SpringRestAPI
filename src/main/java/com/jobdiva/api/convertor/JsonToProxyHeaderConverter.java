package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.proxy.ProxyHeader;

public class JsonToProxyHeaderConverter implements Converter<String, ProxyHeader> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ProxyHeader convert(String source) {
		try {
			return jsonMapper.readValue(source, ProxyHeader.class);
		} catch (IOException e) {
		}
		return null;
	}
}
