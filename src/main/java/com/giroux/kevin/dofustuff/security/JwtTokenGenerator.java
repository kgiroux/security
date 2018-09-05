package com.giroux.kevin.dofustuff.security;

import com.giroux.kevin.dofustuff.commons.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Génère un token JWT
 *
 * @author kgiroux
 */
@Service
public class JwtTokenGenerator {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenGenerator.class);

    /**
     * Clé privée permet de générer le token
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Délai d'expiration du token en secondes, 24h
     */
    private static final long EXPIRATION_DELAY = 24 * 60 * 60;

    /**
     * Génère un token pour le matricule
     *
     * @return
     */
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        // Publication date
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        claims.setIssuedAt(now);
        // Expiration date
        claims.setExpiration(Date
                .from(LocalDateTime.now().plusSeconds(EXPIRATION_DELAY).atZone(ZoneId.systemDefault()).toInstant()));
        // Token id
        claims.setId(user.getId());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
