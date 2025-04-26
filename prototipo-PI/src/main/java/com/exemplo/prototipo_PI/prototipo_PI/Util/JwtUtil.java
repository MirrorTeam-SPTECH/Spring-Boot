package com.exemplo.prototipo_PI.prototipo_PI.Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SecurityException;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Gera uma chave de 256 bits segura para HS256 em runtime
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Validade do token em ms (1 hora)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 60;

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)  // HS256 + chave segura
                .compact();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            throw new RuntimeException("Token JWT inválido ou mal assinado", e);
        }
    }

    public boolean validateToken(String token, String username) {
        final String extracted = extractUsername(token);
        final Date expires = extractExpiration(token);
        return extracted.equals(username) && !expires.before(new Date());
    }
}