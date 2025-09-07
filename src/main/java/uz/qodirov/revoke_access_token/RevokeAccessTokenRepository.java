package uz.qodirov.revoke_access_token;

import org.springframework.stereotype.Repository;
import uz.qodirov.generic.JpaGenericRepository;

@Repository
public interface RevokeAccessTokenRepository extends JpaGenericRepository<RevokeAccessTokenEntity, String> {
    boolean existsByAccessToken(String token);
}
