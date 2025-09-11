package uz.qodirov.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.qodirov.constant.Role;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserService;
import uz.qodirov.user.dto.UserRequest;
import uz.qodirov.user.dto.UserUpdateRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserService userService;

    @Override
    public UserEntity create(TeacherRequest request) {
        log.info("Create teacher request: {}", request);
        UserRequest userRequest = toUserRequest(request);
        return userService.create(userRequest);
    }

    @Override
    public UserEntity update(String id, TeacherRequest request) {
        log.info("Update teacher request: {},id: {}", request, id);
        UserUpdateRequest updateRequest = toUpdateRequest(id, request);
        return userService.update(updateRequest);
    }

    private UserRequest toUserRequest(TeacherRequest request) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(request.getEmail());
        userRequest.setEmail(request.getEmail());
        userRequest.setPassword("123456");
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setMiddleName(request.getMiddleName());
        userRequest.setPhone(request.getPhone());
        userRequest.setRole(Role.TEACHER);
        return userRequest;
    }

    private UserUpdateRequest toUpdateRequest(String id, TeacherRequest request) {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId(id);
        updateRequest.setEmail(request.getEmail());
        updateRequest.setFirstName(request.getFirstName());
        updateRequest.setLastName(request.getLastName());
        updateRequest.setMiddleName(request.getMiddleName());
        updateRequest.setPhone(request.getPhone());
        updateRequest.setEmail(request.getEmail());
        updateRequest.setAdditionalInfo(request.getAdditionalInfo());
        return updateRequest;
    }
}
