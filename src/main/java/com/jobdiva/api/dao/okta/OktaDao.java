package com.jobdiva.api.dao.okta;

import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;

@Component
public class OktaDao extends AbstractJobDivaDao {
	
	public Boolean oktaAccessTokenVerifier(String oktaDomain, String oktaAud, String oktaToken, String email) throws Exception {
		try {
			//
			AccessTokenVerifier jwtVerifier = JwtVerifiers. //
					accessTokenVerifierBuilder() //
					//
					.setIssuer("https://" + oktaDomain + "/oauth2/default")//
					//
					.setAudience(oktaAud) //
					//
					.build();
			//
			Jwt jwt = jwtVerifier.decode(oktaToken);
			//
			String mailFromToken = (String) jwt.getClaims().get("email");
			//
			return mailFromToken != null && mailFromToken.equalsIgnoreCase(email);
			//
		} catch (JwtVerificationException e) {
			throw new Exception(e.getMessage());
		}
		//
	}
}
