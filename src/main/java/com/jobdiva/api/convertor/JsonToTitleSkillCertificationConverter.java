package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.TitleSkillCertification;

public class JsonToTitleSkillCertificationConverter implements Converter<String, TitleSkillCertification> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public TitleSkillCertification convert(String source) {
		try {
			return jsonMapper.readValue(source, TitleSkillCertification.class);
		} catch (IOException e) {
		}
		return null;
	}
}
