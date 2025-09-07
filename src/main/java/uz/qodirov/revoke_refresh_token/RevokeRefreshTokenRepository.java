package uz.qodirov.revoke_refresh_token;

import org.springframework.stereotype.Repository;
import uz.qodirov.generic.JpaGenericRepository;

@Repository
public interface RevokeRefreshTokenRepository extends JpaGenericRepository<RevokeRefreshTokenEntity, String> {
    boolean existsByRefreshToken(String refreshToken);
}
