package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;

/**
 * @author Joseph Chidiac
 *
 *         Apr 29, 2021
 */
public class AbstractService {
	
	@Autowired
	JobDivaConnectivity jobDivaConnectivity;
	
	protected <T> T inTransaction(TransactionCallback<T> transactionCallback) {
		//
		CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		//
		DataSourceTransactionManager txManager = jobDivaConnectivity.getDataSourceTransactionManager(customAuthenticationToken.getJdbcTemplate());
		//
		TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
		//
		return transactionTemplate.execute(transactionCallback);
		//
	}
}
