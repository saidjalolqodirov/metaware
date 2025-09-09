package uz.qodirov.user.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.qodirov.constant.PathNames;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.user.dto.AuthResponse;
import uz.qodirov.user.dto.SignInRequest;
import uz.qodirov.user.dto.TokensRequest;

@RestController
@RequestMapping(PathNames.API + "auth")
@Tag(name = "Auth controller (CP)")
public class AuthControllerCpV1 {
    private final AuthService authService;

    public AuthControllerCpV1(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign_in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest signInRequest) throws DataNotFoundException {
        AuthResponse authResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokensRequest tokensRequest) {
        authService.logout(tokensRequest);
        return ResponseEntity.ok(null);
    }

}
