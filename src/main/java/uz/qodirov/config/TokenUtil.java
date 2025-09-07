package uz.qodirov.config;

import io.jsonwebtoken.Claims;

public interface TokenUtil {
    String getUsernameFromToken(String token);

    String generateAccessToken(UserDetailsImpl userDetails);

    String generateRefreshToken(UserDetailsImpl userDetails);

    Boolean validateToken(String token);

    void revokeTokens(String accessToken, String refreshToken);

    Claims getClaims(String token);
}
