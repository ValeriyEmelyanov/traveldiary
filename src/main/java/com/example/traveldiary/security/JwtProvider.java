package com.example.traveldiary.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final String jwtSecret;
    private final int jwtDuration;

    @Autowired
    public JwtProvider(@Value("${jwt.secret}") String jwtSecret,
                       @Value("${jwt.duration}") int jwtDuration) {
        this.jwtSecret = jwtSecret;
        this.jwtDuration = jwtDuration;
    }

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now()
                .plusDays(jwtDuration)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Token expired");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException ex) {
            log.error("Malformed jwt");
        } catch (SignatureException ex) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("Invalid token");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
