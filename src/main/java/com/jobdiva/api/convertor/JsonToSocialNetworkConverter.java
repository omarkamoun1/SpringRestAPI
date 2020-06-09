package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.SocialNetwork;

public class JsonToSocialNetworkConverter implements Converter<String, SocialNetwork> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SocialNetwork convert(String source) {
		try {
			return jsonMapper.readValue(source, SocialNetwork.class);
		} catch (IOException e) {
		}
		return null;
	}
}
