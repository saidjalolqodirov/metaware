package uz.qodirov.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qodirov.constant.Privilege;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;

import java.util.Collection;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String imageId;
    private Role role;
    private Status status;
    private Collection<Privilege> privileges;
    private Long createdDate;
    private Long modifiedDate;
}
