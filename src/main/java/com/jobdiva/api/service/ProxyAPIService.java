package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.proxy.ProxyAPIDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.proxy.ProxyHeader;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;

@Service
public class ProxyAPIService {
	
	@Autowired
	ProxyAPIDao proxyAPIDao;
	
	public Response proxyAPI(JobDivaSession jobDivaSession, String method, String url, ProxyHeader[] headers, ProxyParameter[] parameters, String body) throws Error {
		try {
			return proxyAPIDao.proxyAPI(jobDivaSession, method, url, headers, parameters, body);
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}
}
