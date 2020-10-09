package com.jobdiva.api.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class SimpleCORSFilter implements Filter {
	
	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);
	
	public SimpleCORSFilter() {
		log.info("SimpleCORSFilter init");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//
		chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
	}
	
	@Override
	public void destroy() {
	}
}