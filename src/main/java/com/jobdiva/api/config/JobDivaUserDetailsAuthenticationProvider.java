package com.jobdiva.api.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.jobdiva.api.service.api.JobDivaUserDetailsService;

public class JobDivaUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	/**
	 * The plaintext password used to perform
	 * PasswordEncoder#matches(CharSequence, String)} on when the user is not
	 * found to avoid SEC-2056.
	 */
	private static final String			USER_NOT_FOUND_PASSWORD	= "userNotFoundPassword";
	private PasswordEncoder				passwordEncoder;
	private JobDivaUserDetailsService	userDetailsService;
	/**
	 * The password used to perform
	 * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is
	 * not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the
	 * password is not in a valid format.
	 */
	private String						userNotFoundEncodedPassword;
	
	public JobDivaUserDetailsAuthenticationProvider(PasswordEncoder passwordEncoder, JobDivaUserDetailsService userDetailsService) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}
	
	@Override
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
		this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
	}
	
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
		UserDetails loadedUser;
		try {
			loadedUser = this.userDetailsService.loadUserByUsernameAndClientId(auth.getClientId(), auth.getPrincipal().toString(), auth.getCredentials().toString(), auth.getCheckApiPermission());
		} catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
				String presentedPassword = authentication.getCredentials().toString();
				passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
			}
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}
		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException("UserDetailsService returned null, " + "which is an interface contract violation");
		}
		return loadedUser;
	}
	
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		// Ensure we return the original credentials the user supplied,
		// so subsequent attempts are successful even with encoded passwords.
		// Also ensure we return the original getDetails(), so that future
		// authentication events after cache expiry contain the details
		CustomAuthenticationToken result = new CustomAuthenticationToken(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());
		result.setClientId(((CustomAuthenticationToken) authentication).getClientId());
		result.setJdbcTemplate(((CustomAuthenticationToken) authentication).getJdbcTemplate());
		result.setNamedParameterJdbcTemplate(((CustomAuthenticationToken) authentication).getNamedParameterJdbcTemplate());
		return result;
	}
}