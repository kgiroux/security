package com.giroux.kevin.dofustuff.security;

import com.giroux.kevin.dofustuff.commons.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * Génère un token JWT
 *
 * @author scadot
 *
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
    private static long EXPIRATION_DELAY = 24 * 60 * 60;

    /**
     * Génère un token pour le matricule
     *
     * @return
     */
    public String generateToken(final User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
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
