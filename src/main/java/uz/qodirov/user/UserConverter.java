package uz.qodirov.user;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;
import uz.qodirov.user.dto.UserDto;

@Component
public class UserConverter extends Converter<UserDto, UserEntity> {
    public UserConverter() {
        super(userResponse -> new UserEntity(),
                userEntity -> new UserDto(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getPhone(),
                        userEntity.getImageId(),
                        userEntity.getRole(),
                        userEntity.getStatus(),
                        userEntity.getCreatedDate(),
                        userEntity.getModifiedDate()
                ));
    }
}
