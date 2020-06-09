package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Skill;

public class JsonToSkillConverter implements Converter<String, Skill> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Skill convert(String source) {
		try {
			return jsonMapper.readValue(source, Skill.class);
		} catch (IOException e) {
		}
		return null;
	}
}
