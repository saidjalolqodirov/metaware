package uz.qodirov.revoke_refresh_token;


import uz.qodirov.generic.JpaGenericService;


public interface RevokeRefreshTokenService extends JpaGenericService<RevokeRefreshTokenEntity, String> {
    void create(String refreshToken, String userId);

    boolean isRevoked(String token);
}
