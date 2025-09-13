package uz.qodirov.revoke.access;

import org.springframework.stereotype.Service;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;

@Service
public class RevokeAccessTokenServiceImpl extends JpaGenericServiceImpl<RevokeAccessTokenEntity, String> implements RevokeAccessTokenService {

    private final RevokeAccessTokenRepository revokeAccessTokenRepository;

    public RevokeAccessTokenServiceImpl(RevokeAccessTokenRepository revokeAccessTokenRepository) {
        this.revokeAccessTokenRepository = revokeAccessTokenRepository;
    }

    @Override
    protected JpaGenericRepository<RevokeAccessTokenEntity, String> getRepository() {
        return revokeAccessTokenRepository;
    }

    @Override
    public void create(String accessToken, String userId) {
        RevokeAccessTokenEntity model = new RevokeAccessTokenEntity(accessToken, userId);
        model.setCreatedDate(System.currentTimeMillis());
        save(model);
    }

    @Override
    public boolean isRevoked(String token) {
        return revokeAccessTokenRepository.existsByAccessToken(token);
    }
}
