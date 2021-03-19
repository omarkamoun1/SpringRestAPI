package com.jobdiva.api.dao.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobdiva.api.config.jwt.JwtTokenUtil;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class JobDivaSessionDao extends AbstractJobDivaDao {
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	public String refreshToken(JobDivaSession jobDivaSession) {
		//
		String token = jwtTokenUtil.generateToken(jobDivaSession);
		//
		return token;
	}
}
