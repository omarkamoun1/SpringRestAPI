package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.BillingUnitType;

public class JsonToBillingUnitTypeConverter implements Converter<String, BillingUnitType> {
	
	@Override
	public BillingUnitType convert(String source) {
		for (BillingUnitType BillingUnitType : BillingUnitType.values()) {
			if (BillingUnitType.getValue().equalsIgnoreCase(source)) {
				return BillingUnitType;
			}
		}
		return null;
	}
}
