package com.jobdiva.api.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class JobDivaException extends RuntimeException {
	
	public JobDivaException(String... searchParamsMap) {
		super(JobDivaException.generateMessage(toMap(String.class, String.class, searchParamsMap)));
	}
	
	private static String generateMessage(Map<String, String> searchParams) {
		return StringUtils.capitalize("JobDiva API : ") + " was not found for parameters " + searchParams;
	}
	
	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException("Invalid entries");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new, (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}
}