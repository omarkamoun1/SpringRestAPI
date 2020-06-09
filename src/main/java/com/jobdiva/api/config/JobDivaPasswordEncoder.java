package com.jobdiva.api.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class JobDivaPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword != null && rawPassword.toString().equals(encodedPassword);
	}

}
