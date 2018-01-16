package com.giroux.kevin.dofustuff.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Gestionnaire d'erreur lors de l'authentification - renvoie une 401
 * @author scadot
 *
 */
@Component
public class JwtAuthenticationUnauthorizedHandler implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6097504050440514508L;

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		
	}

}
