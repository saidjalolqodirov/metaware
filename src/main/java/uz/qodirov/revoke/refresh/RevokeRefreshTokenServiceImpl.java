package uz.qodirov.revoke.refresh;

import org.springframework.stereotype.Service;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;


@Service
public class RevokeRefreshTokenServiceImpl extends JpaGenericServiceImpl<RevokeRefreshTokenEntity, String> implements RevokeRefreshTokenService {

    private final RevokeRefreshTokenRepository revokeRefreshTokenRepository;

    public RevokeRefreshTokenServiceImpl(RevokeRefreshTokenRepository revokeRefreshTokenRepository) {
        this.revokeRefreshTokenRepository = revokeRefreshTokenRepository;
    }

    @Override
    protected JpaGenericRepository<RevokeRefreshTokenEntity, String> getRepository() {
        return revokeRefreshTokenRepository;
    }

    @Override
    public void create(String refreshToken, String userId) {
        RevokeRefreshTokenEntity model = new RevokeRefreshTokenEntity(refreshToken, userId);
        model.setCreatedDate(System.currentTimeMillis());
        save(model);
    }

    @Override
    public boolean isRevoked(String token) {
        return revokeRefreshTokenRepository.existsByRefreshToken(token);
    }
}
