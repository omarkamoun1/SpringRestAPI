package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.GroupInvoiceByType;

public class JsonToGroupInvoiceByTypeConverter implements Converter<String, GroupInvoiceByType> {
	
	@Override
	public GroupInvoiceByType convert(String source) {
		for (GroupInvoiceByType GroupInvoiceByType : GroupInvoiceByType.values()) {
			if (GroupInvoiceByType.getValue().equalsIgnoreCase(source)) {
				return GroupInvoiceByType;
			}
		}
		return null;
	}
}
