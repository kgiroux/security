package com.giroux.kevin.dofustuff.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validateur du token JWT
 * 
 * @author scadot
 *
 */
@Component
public class JwtTokenValidator {

	/**
	 * Logger 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenValidator.class);
	
	/**
	 * Clé secrète
	 */
	private static final String secret = "dofus-stuff";

	/**
	 * Méthode permettant de parser le token fourni et d'en déduire les
	 * informations du user. Null si le token n'est pas valide
	 * 
	 * @param token
	 * @return
	 */
	public Claims parseToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (JwtException e) {
			LOG.error("Error during the token parsing. {}", e);
		}
		return claims;
	}
}