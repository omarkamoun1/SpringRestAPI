package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.AddExpenseInvoiceDef;

public class JsonToAddExpenseInvoiceDefConverter implements Converter<String, AddExpenseInvoiceDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddExpenseInvoiceDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddExpenseInvoiceDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
