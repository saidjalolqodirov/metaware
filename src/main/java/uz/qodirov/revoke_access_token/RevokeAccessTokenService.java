package uz.qodirov.revoke_access_token;

import uz.qodirov.generic.JpaGenericService;

public interface RevokeAccessTokenService extends JpaGenericService<RevokeAccessTokenEntity, String> {
    void create(String accessToken, String userId);

    boolean isRevoked(String token);
}
