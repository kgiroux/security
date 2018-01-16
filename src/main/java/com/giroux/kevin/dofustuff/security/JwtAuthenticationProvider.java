package com.giroux.kevin.dofustuff.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.giroux.kevin.dofustuff.security.exceptions.JwtTokenMalformedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/**
 * Classe permettant de vérifier la validité du token fournit
 * 
 * @author scadot
 *
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtTokenValidator jwtTokenValidator;

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();
		Claims parsedUser = jwtTokenValidator.parseToken(token);
		checkJWToken(parsedUser);
		Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		Date tokenExpirationDate = parsedUser.getExpiration();
		return new User(parsedUser.getSubject(), "NoNeeded", true, true, tokenExpirationDate.after(now), true, null);
	}

	/**
	 * Méthode permettant d'effectuer des contrôles sur le token
	 * @param token
	 */
	private void checkJWToken(final Claims token){
		if (token == null) {
			throw new JwtTokenMalformedException("JWT token is not valid");
		}
	}
}
