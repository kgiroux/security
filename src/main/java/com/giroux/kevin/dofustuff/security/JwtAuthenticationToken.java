package com.giroux.kevin.dofustuff.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * DTO juste pour contenir le token (les autres champs ne sont pas utilisés mais
 * sont nécessaires pour la classe mère)
 * 
 * @author kgiroux
 *
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * serial id 
	 */
	private static final long serialVersionUID = 6672995431908715748L;
	
	/**
	 * Token transmis
	 */
	private String token;

	/**
	 * Constructeur
	 * @param token
	 */
	public JwtAuthenticationToken(String token) {
		super(null, null);
		this.token = token;
	}

	
	public String getToken() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
}
