package uz.qodirov.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordRequest {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
