package uz.qodirov.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.qodirov.config.TokenUtil;
import uz.qodirov.config.UserDetailsImpl;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.exception.BadRequestException;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.exception.UserNotActivatedException;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserService;
import uz.qodirov.user.dto.AuthResponse;
import uz.qodirov.user.dto.SignInRequest;
import uz.qodirov.user.dto.TokensRequest;
import uz.qodirov.user.dto.UserRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
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
        UserEntity userEntity = userService.findByUsername(signInRequest.getUsername());
        if (userEntity != null) {
            if (!userEntity.getStatus().equals(Status.ACTIVE)) {
                throw new UserNotActivatedException(signInRequest.getUsername());
            }
            if (!passwordEncoder.matches(signInRequest.getPassword(), userEntity.getPassword())) {
                throw new BadRequestException(StatusEnum.USERNAME_OR_PASSWORD_NOT_MATCH, null);
            }
        } else {
            throw new DataNotFoundException(StatusEnum.USER_NOT_FOUND, null);
        }
        return createAuthResponse(userEntity);
    }

    @Override
    public void logout(TokensRequest tokensRequest) {
        tokenUtil.revokeTokens(tokensRequest.getAccessToken(), tokensRequest.getRefreshToken());
    }

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        UserEntity userEntity = userService.create(toUserRequest(request));
        return createAuthResponse(userEntity);
    }

    private UserRequest toUserRequest(SignUpRequest request) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(request.getUsername());
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setMiddleName(request.getMiddleName());
        userRequest.setEmail(request.getEmail());
        userRequest.setPhone(request.getPhone());
        userRequest.setRole(Role.STUDENT);
        userRequest.setPassword("123456");
        return userRequest;
    }

    private AuthResponse createAuthResponse(UserEntity userEntity) {
        UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
        String accessToken = tokenUtil.generateAccessToken(userDetails);
        String refreshToken = tokenUtil.generateRefreshToken(userDetails);
        return new AuthResponse(accessToken, refreshToken, userEntity.getId(), userEntity.getRole(), tokenType, Long.parseLong(accessExpirationTime), Long.parseLong(refreshExpirationTime));
    }


}
