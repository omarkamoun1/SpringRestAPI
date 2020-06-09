package com.jobdiva.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.jobdiva.api.config.jwt.JwtAuthenticationEntryPoint;
import com.jobdiva.api.config.jwt.JwtRequestFilter;
import com.jobdiva.api.service.api.JobDivaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[]		AUTH_WHITELIST	= {
			// -- swagger ui //
			// "/doc/api" //
			"/v2/api-docs",														//
			"/swagger-resources",												//
			"/swagger-resources/**",											//
			"/configuration/ui",												//
			"/configuration/security",											//
			"/swagger-ui.html",													//
			"/jobdiva-api.html",												//
			"/logo.png",														//
			"/logo/logo.png",													//
			"/webjars/**",														//
			"/swagger*",														//
			"/csrf"																//
			// other public endpoints of your API may be appended to this array
	};
	//
	@Autowired
	private JwtAuthenticationEntryPoint	jwtAuthenticationEntryPoint;
	//
	@Autowired
	private UserCache					userCache;
	//
	@Autowired
	private JwtRequestFilter			jwtRequestFilter;
	//
	@Autowired
	private JobDivaUserDetailsService	jobDivaUserDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use JobDivaPasswordEncoder BCryptPasswordEncoder
		auth.authenticationProvider(authProvider());
	}
	
	public AuthenticationProvider authProvider() {
		JobDivaUserDetailsAuthenticationProvider provider = new JobDivaUserDetailsAuthenticationProvider(passwordEncoder(), jobDivaUserDetailsService);
		provider.setUserCache(userCache);
		jwtRequestFilter.setUserCache(userCache);
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new JobDivaPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JobdivaAuthenticationFilter authenticationFilter() throws Exception {
		JobdivaAuthenticationFilter filter = new JobdivaAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		// filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().//
				antMatchers("/api/authenticate").permitAll().//
				antMatchers("/api/controller/saveMachineId").permitAll().//
				antMatchers("/api/controller/getCoddlers").permitAll().//
				antMatchers("/api/controller/getConfiguration").permitAll().//
				antMatchers(AUTH_WHITELIST).permitAll().
				// antMatchers("/api/bi/*").hasRole("BAR").
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// anyRequest().permitAll().and().
				//
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//
		httpSecurity.addFilterBefore(jwtRequestFilter, JobdivaAuthenticationFilter.class);
		//
		//
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// @formatter:off
		super.configure(web);
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		// firewall.setAllowSemicolon(true);
		return firewall;
	}
}