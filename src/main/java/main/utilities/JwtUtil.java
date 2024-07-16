package main.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET = "v/RIIb8O9RmiDrfG/Ax6RGDQ7jH7G+eZ1IyLK0T/Kl4=";
    private static final long EXPIRATION_TIME = 864_00_000; // 1 day
    // Create a signing key from the provided secret
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public static Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, String email) {
        return !isTokenExpired(token) && extractUsername(token).equals(email);
    }

    public static String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 5 * 60)) // 5 minutes expiration time
                .signWith(key)
                .compact();
    }
}
