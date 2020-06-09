package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.TimeSheetEntryFormatType;

public class JsonToTimeSheetEntryFormatTypeConverter implements Converter<String, TimeSheetEntryFormatType> {
	
	@Override
	public TimeSheetEntryFormatType convert(String source) {
		for (TimeSheetEntryFormatType TimeSheetEntryFormatType : TimeSheetEntryFormatType.values()) {
			if (TimeSheetEntryFormatType.getValue().equalsIgnoreCase(source)) {
				return TimeSheetEntryFormatType;
			}
		}
		return null;
	}
}
