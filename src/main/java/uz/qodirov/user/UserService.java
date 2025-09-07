package uz.qodirov.user;


import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.generic.JpaGenericService;
import uz.qodirov.user.dto.ChangePasswordRequest;
import uz.qodirov.user.dto.UserRequest;
import uz.qodirov.user.dto.UserUpdateRequest;

public interface UserService extends JpaGenericService<UserEntity, String> {

    UserEntity create(UserRequest request);

    UserEntity update(UserUpdateRequest request);

    UserEntity changePassword(String id, ChangePasswordRequest request);

    UserEntity getMe() throws DataNotFoundException;

}
