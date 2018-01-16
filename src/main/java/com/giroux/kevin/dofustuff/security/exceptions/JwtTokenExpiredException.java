package com.giroux.kevin.dofustuff.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2221062299201737485L;

	public JwtTokenExpiredException() {
		super("Token expired");
	}
}
