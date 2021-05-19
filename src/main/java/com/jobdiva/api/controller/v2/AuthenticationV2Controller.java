package com.jobdiva.api.controller.v2;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.jobdiva.api.config.jwt.JwtTokenUtil;
import com.jobdiva.api.dao.authenticate.JobDivaAuthenticateDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.authenticate.JwtV2Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Authentication API", description = "REST API Used For Authentication")
@RestController
@RequestMapping("/apiv2/")
@CrossOrigin
public class AuthenticationV2Controller {
	
	//
	@Autowired
	private AuthenticationManager	authenticationManager;
	//
	@Autowired
	JobDivaAuthenticateDao			jobDivaAuthenticate;
	//
	@Autowired
	JwtTokenUtil					jwtTokenUtil;
	//
	@Autowired
	private UserCache				userCache;
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "password", required = true, dataType = "String", format = "password") })
	@ApiOperation(value = "Authenticate")
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	public String createAuthenticationToken(
			//
			@ApiParam(value = "Provided by JobDiva", required = true, type = "Long") //
			@RequestParam(required = true) Long clientid, //
			//
			@ApiParam(value = "Account username (email)", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "Account password", required = true, type = "string", format = "password") //
			@RequestParam(required = true) String password) throws Exception {
		//
		userCache.removeUserFromCache(username);
		//
		JobDivaSession jobDivaSession = authenticate(clientid, username, password, true);
		//
		//
		final String token = jwtTokenUtil.generateToken(jobDivaSession);
		//
		return token;
		//
		//
		//
	}
	//
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "password", required = true, dataType = "String", format = "password") })
	@ApiOperation(value = "V2 Authenticate")
	@RequestMapping(value = "/v2/authenticate", method = RequestMethod.GET)
	public JwtV2Response createV2AuthenticationToken(
			//
			@ApiParam(value = "Provided by JobDiva", required = true, type = "Long") //
			@RequestParam(required = true) Long clientid, //
			//
			@ApiParam(value = "Account username (email)", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "Account password", required = true, type = "string", format = "password") //
			@RequestParam(required = true) String password) throws Exception {
		//
		userCache.removeUserFromCache(username);
		//
		JobDivaSession jobDivaSession = authenticate(clientid, username, password, true);
		//
		//
		JwtV2Response response = createJwtV2ResponseBySession(jobDivaSession);
		//
		return response;
		//
		//
		//
	}
	
	private JwtV2Response createJwtV2ResponseBySession(JobDivaSession jobDivaSession) {
		String token = jwtTokenUtil.generateToken(jobDivaSession);
		String refreshtoken = jwtTokenUtil.generateRefreshToken(jobDivaSession);
		//
		JwtV2Response response = new JwtV2Response(token, refreshtoken);
		return response;
	}
	
	@ApiOperation(value = "Refresh Token")
	@RequestMapping(value = "/v2/refreshToken", method = RequestMethod.GET)
	public JwtV2Response refreshToken(
	//
	) throws Exception {
		//
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			JobDivaSession jobDivaSession = (JobDivaSession) authentication.getPrincipal();
			if (jobDivaSession != null) {
				//
				JwtV2Response response = createJwtV2ResponseBySession(jobDivaSession);
				//
				return response;
			}
		}
		//
		throw new AuthenticationException("Token is invalid.");
		//
		//
	}
	
	protected JobDivaSession authenticate(Long clientId, String username, String password, Boolean checkApiPermission) throws Exception {
		try {
			//
			//
			userCache.removeUserFromCache(username);
			//
			Authentication authenticate = authenticationManager.authenticate(new CustomAuthenticationToken(username, password, clientId, checkApiPermission));
			return (JobDivaSession) authenticate.getPrincipal();
			//
			//
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	protected JobDivaSession checkAuthenticate(String username, String password) throws Exception {
		try {
			//
			//
			Authentication authenticate = authenticationManager.authenticate(new CustomAuthenticationToken(username, password, true));
			return (JobDivaSession) authenticate.getPrincipal();
			//
			//
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}