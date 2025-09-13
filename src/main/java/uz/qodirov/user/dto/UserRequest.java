package uz.qodirov.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.qodirov.constant.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty(message = "username is null")
    private String username;

    @NotEmpty(message = "Email is null")
    private String email;

    @NotEmpty
    @Size(min = 3)
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String middleName;

    private String phone;

    @NotNull(message = "Role is null")
    private Role role;

    private String imageId;

    private HashMap<String, Object> additionalInfo;

}
