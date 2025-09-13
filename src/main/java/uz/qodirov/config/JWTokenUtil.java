package uz.qodirov.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.qodirov.exception.TokenExpiredException;
import uz.qodirov.revoke.access.RevokeAccessTokenService;
import uz.qodirov.revoke.refresh.RevokeRefreshTokenService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTokenUtil implements TokenUtil {

    private final RevokeRefreshTokenService revokeRefreshTokenService;
    private final RevokeAccessTokenService revokeAccessTokenService;

    @Value("${app.jjwt.secret}")
    private String secret;

    @Value("${app.jjwt.access_expiration}")
    private String accessExpirationTime;

    @Value("${app.jjwt.refresh_expiration}")
    private String refreshExpirationTime;

    public JWTokenUtil(RevokeRefreshTokenService revokeRefreshTokenService, RevokeAccessTokenService revokeAccessTokenService) {
        this.revokeRefreshTokenService = revokeRefreshTokenService;
        this.revokeAccessTokenService = revokeAccessTokenService;
    }

    private Claims getAllClaimsFromToken(String token) throws TokenExpiredException {
        Claims claims;
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token expired");
        }
        return claims;
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Date getExpirationDateFromToken(String token) throws TokenExpiredException {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration;
        try {
            expiration = getExpirationDateFromToken(token);
        } catch (TokenExpiredException e) {
            return true;
        }
        return expiration.before(new Date());
    }

    @Override
    public String generateAccessToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("enabled", userDetails.getEnabled());
        return doGenerateToken(claims, userDetails.getId(), accessExpirationTime);
    }

    @Override
    public String generateRefreshToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("enabled", userDetails.getEnabled());
        return doGenerateToken(claims, userDetails.getId(), refreshExpirationTime);
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String expirationTime) {
        long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    @Override
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public void revokeTokens(String accessToken, String refreshToken) {
        if (!isTokenExpired(accessToken)) {
            Claims claims = getClaims(accessToken);
            String id = (String) claims.get("id");
            revokeAccessTokenService.create(accessToken, id);
        }
        if (!isTokenExpired(refreshToken)) {
            Claims claims = getClaims(refreshToken);
            String id = (String) claims.get("id");
            revokeRefreshTokenService.create(refreshToken, id);
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims;
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

}
