package com.giroux.kevin.dofustuff.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2221062299201737485L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}
}
