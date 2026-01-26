package com.jnrptt.reservasapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

public class JwtService {
    private final SecretKey secretKey;
    private final long expirationMillis;
    private final Clock clock;

    public JwtService(String secret, long expirationMillis, Clock clock) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("Secret debe tener al menos 32 caracteres");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
        this.clock = clock;
    }

    public String generarToken(String username) {
        Instant now = clock.instant();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(secretKey)
                .compact();
    }

    public boolean esTokenValido(String token) {
        try {
            Claims claims = extraerClaims(token);
            return !claims.getExpiration().before(Date.from(clock.instant()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    public Instant extraerExpiracion(String token) {
        return extraerClaims(token).getExpiration().toInstant();
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clock(() -> Date.from(clock.instant()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}