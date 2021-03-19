package com.jobdiva.api.config.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Joseph Chidiac
 *
 */
public class MultiRoutingDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataBaseContextHolder.getCurrentDb();
	}
}