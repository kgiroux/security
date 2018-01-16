package com.giroux.kevin.dofustuff.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception levée lorsque le token JWT ne peut être parsé
 * 
 * @author scadot
 *
 */
public class JwtTokenMalformedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4426447114004338066L;

	public JwtTokenMalformedException(String msg) {
		super(msg);
	}
}
