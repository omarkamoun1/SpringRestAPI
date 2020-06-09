package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.ShowOnInvoiceType;

public class JsonToShowOnInvoiceTypeConverter implements Converter<String, ShowOnInvoiceType> {
	
	@Override
	public ShowOnInvoiceType convert(String source) {
		for (ShowOnInvoiceType showOnInvoiceType : ShowOnInvoiceType.values()) {
			if (showOnInvoiceType.getValue().equalsIgnoreCase(source)) {
				return showOnInvoiceType;
			}
		}
		return null;
	}
}
