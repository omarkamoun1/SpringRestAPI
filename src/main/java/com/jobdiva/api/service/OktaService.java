package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.okta.OktaDao;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class OktaService {
	
	@Autowired
	OktaDao oktaDao;
	
	public Boolean oktaAccessTokenVerifier(String issuer, String oktaAud, String oktaToken, String email) throws Exception {
		//
		return oktaDao.oktaAccessTokenVerifier(issuer, oktaAud, oktaToken, email);
	}
}
