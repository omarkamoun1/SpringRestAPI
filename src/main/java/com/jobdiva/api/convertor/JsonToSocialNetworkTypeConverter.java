package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.SocialNetworkType;

public class JsonToSocialNetworkTypeConverter implements Converter<String, SocialNetworkType> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SocialNetworkType convert(String source) {
		try {
			return jsonMapper.readValue(source, SocialNetworkType.class);
		} catch (IOException e) {
		}
		return null;
	}
}
