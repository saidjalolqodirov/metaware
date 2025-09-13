package uz.qodirov.revoke.refresh;

import org.springframework.stereotype.Repository;
import uz.qodirov.generic.JpaGenericRepository;

@Repository
public interface RevokeRefreshTokenRepository extends JpaGenericRepository<RevokeRefreshTokenEntity, String> {
    boolean existsByRefreshToken(String refreshToken);
}
