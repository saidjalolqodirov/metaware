package uz.qodirov.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.qodirov.config.TokenUtil;
import uz.qodirov.config.UserDetailsImpl;
import uz.qodirov.constant.Status;
import uz.qodirov.exception.UserNotActivatedException;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserRepository;
import uz.qodirov.user.dto.AuthResponse;
import uz.qodirov.user.dto.SignInRequest;
import uz.qodirov.user.dto.TokensRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jjwt.tokenType}")
    private String tokenType;
    @Value("${app.jjwt.access_expiration}")
    private String accessExpirationTime;
    @Value("${app.jjwt.refresh_expiration}")
    private String refreshExpirationTime;

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        Optional<UserEntity> optional = userRepository.findFirstByUsername(signInRequest.getUsername());
        if (optional.isPresent()) {
            if (!optional.get().getStatus().equals(Status.ACTIVE)) {
                throw new UserNotActivatedException(signInRequest.getUsername());
            }
            if (!passwordEncoder.matches(signInRequest.getPassword(), optional.get().getPassword())) {
                throw new BadCredentialsException(signInRequest.getUsername());
            }
        } else {
            throw new BadCredentialsException(signInRequest.getUsername());
        }
        return createAuthResponse(optional.get());
    }

    @Override
    public void logout(TokensRequest tokensRequest) {
        tokenUtil.revokeTokens(tokensRequest.getAccessToken(), tokensRequest.getRefreshToken());
    }

    private AuthResponse createAuthResponse(UserEntity userEntity) {
        UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
        String accessToken = tokenUtil.generateAccessToken(userDetails);
        String refreshToken = tokenUtil.generateRefreshToken(userDetails);
        return new AuthResponse(accessToken, refreshToken, userEntity.getId(), userEntity.getRole(), tokenType, Long.parseLong(accessExpirationTime), Long.parseLong(refreshExpirationTime));
    }


}
