package uz.qodirov.revoke.refresh;


import uz.qodirov.generic.JpaGenericService;


public interface RevokeRefreshTokenService extends JpaGenericService<RevokeRefreshTokenEntity, String> {
    void create(String refreshToken, String userId);

    boolean isRevoked(String token);
}
