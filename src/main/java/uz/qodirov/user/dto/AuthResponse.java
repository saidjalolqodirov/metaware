package uz.qodirov.user.dto;

import lombok.*;
import uz.qodirov.constant.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private Role role;
    private String tokenType;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
