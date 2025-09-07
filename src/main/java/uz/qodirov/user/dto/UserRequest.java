package uz.qodirov.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.qodirov.constant.Privilege;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty(message = "username is null")
    private String username;

    @NotEmpty
    @Size(min = 3)
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String phone;

    @NotNull(message = "Role is null")
    private Role role;

    @NotNull(message = "status is null")
    private Status status;

    private String imageId;

    private Collection<Privilege> privileges;

}
