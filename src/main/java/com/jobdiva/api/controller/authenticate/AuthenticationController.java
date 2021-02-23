package com.jobdiva.api.controller.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Authentication API", description = "REST API Used For Authentication")
@RestController
@RequestMapping("/api/")
@CrossOrigin
public class AuthenticationController {
	
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
	
	@ApiIgnore
	@RequestMapping(value = "/internalAuthenticate", method = RequestMethod.GET)
	public String authenticateWithoutCheckPermission(
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
		//
		userCache.removeUserFromCache(username);
		//
		JobDivaSession jobDivaSession = authenticate(clientid, username, password, false);
		//
		//
		final String token = jwtTokenUtil.generateToken(jobDivaSession);
		//
		return token;
		//
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
	
	@ApiIgnore
	@RequestMapping(value = "/appAuthenticate", method = RequestMethod.GET)
	public String appAuthenticate(
			//
			@ApiParam(value = "Account username (email)", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "Account password", required = true, type = "string", format = "password") //
			@RequestParam(required = true) String password) throws Exception {
		//
		//
		userCache.removeUserFromCache(username);
		//
		JobDivaSession jobDivaSession = checkAuthenticate(username, password);
		//
		//
		final String token = jwtTokenUtil.generateToken(jobDivaSession);
		//
		return token;
		//
		//
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