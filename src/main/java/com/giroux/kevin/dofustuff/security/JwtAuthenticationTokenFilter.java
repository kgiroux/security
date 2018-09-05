package com.giroux.kevin.dofustuff.security;

import com.giroux.kevin.dofustuff.security.exceptions.JwtTokenMissingException;
import com.giroux.kevin.dofustuff.utils.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre gérant le token et les droits d'accès
 *
 * @author kgiroux
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private final String tokenHeader;

    public JwtAuthenticationTokenFilter(String tokenHeader) {
        super("/**");
        this.tokenHeader = tokenHeader;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader("ORIGIN") != null) {
            String origin = request.getHeader("ORIGIN");
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }

        if ("OPTIONS".equals(request.getMethod())) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
            return null;
        }

        String header = request.getHeader(tokenHeader);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("No JWT token found in request headers");
        }
        String authToken = header.substring(7);
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        if (checkAuthorization()) {
            chain.doFilter(request, response);
        } else {
            throw new ForbiddenException("You can not access to this page");
        }
    }

    private boolean checkAuthorization() {
        return true;
    }
}
