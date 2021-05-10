package com.jobdiva.api.dao.okta;

import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;

@Component
public class OktaDao extends AbstractJobDivaDao {
	
	public Boolean oktaAccessTokenVerifier(String issuer, String oktaAud, String oktaToken, String email) throws Exception {
		try {
			//
			AccessTokenVerifier jwtVerifier = JwtVerifiers. //
					accessTokenVerifierBuilder() //
					//
					.setIssuer(issuer)//
					//
					.setAudience(oktaAud) //
					//
					.build();
			//
			Jwt jwt = jwtVerifier.decode(oktaToken);
			//
			String mailFromToken = (String) jwt.getClaims().get("email");
			//
			logger.info(issuer + "/" + oktaAud + "/" + oktaToken + "/" + email + "/" + mailFromToken);
			//
			return mailFromToken != null && mailFromToken.equalsIgnoreCase(email);
			//
		} catch (JwtVerificationException e) {
			String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			logger.info(issuer + "/" + oktaAud + "/" + oktaToken + "/" + email + "[ERROR:" + message + "]");
			throw new Exception(message);
		}
		//
	}
}
