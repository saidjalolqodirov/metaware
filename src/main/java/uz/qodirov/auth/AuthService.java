package uz.qodirov.auth;

import uz.qodirov.user.dto.AuthResponse;
import uz.qodirov.user.dto.SignInRequest;
import uz.qodirov.user.dto.TokensRequest;

public interface AuthService {
    AuthResponse signIn(SignInRequest signInRequest);

    void logout(TokensRequest tokensRequest);

    AuthResponse signUp(SignUpRequest request);
}
