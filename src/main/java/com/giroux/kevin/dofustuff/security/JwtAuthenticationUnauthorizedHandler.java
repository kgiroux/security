package com.giroux.kevin.dofustuff.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Gestionnaire d'erreur lors de l'authentification - renvoie une 401
 *
 * @author kgiroux
 */
@Component
public class JwtAuthenticationUnauthorizedHandler implements AuthenticationEntryPoint, Serializable {


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationUnauthorizedHandler.class);
    /**
     *
     */
    private static final long serialVersionUID = -6097504050440514508L;

    @Override
    public void commence(HttpServletRequest arg0, HttpServletResponse response, AuthenticationException arg2)
            throws IOException, ServletException {
        LOGGER.error("Rejected");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }

}
