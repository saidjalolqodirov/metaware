package uz.qodirov.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.qodirov.config.UserDetailsImpl;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.exception.BadRequestException;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.exception.UsernameAlreadyExistException;
import uz.qodirov.file.FileService;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;
import uz.qodirov.user.dto.ChangePasswordRequest;
import uz.qodirov.user.dto.UserRequest;
import uz.qodirov.user.dto.UserUpdateRequest;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends JpaGenericServiceImpl<UserEntity, String> implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Override
    public UserEntity create(UserRequest request) {
        log.info("=========== UserServiceImpl.create ==========");
        UserEntity user = new UserEntity();
        check(request);
        requestToModel(user, request);
        return save(user);
    }

    @Override
    public UserEntity update(UserUpdateRequest request) {
        log.info("============ UserServiceImpl.update==========");
        UserEntity user = findById(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setImage(request.getImageId() == null ? null : fileService.findById(request.getImageId()));
        return save(user);
    }

    @Override
    public UserEntity changePassword(String id, ChangePasswordRequest request) {
        log.info("============ UserServiceImpl.changePassword ========== \n id: {}\n request: {}", id, request);
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException(StatusEnum.VALIDATION_ERROR, new HashMap<>() {{
                put("message", "Passwords do not match");
            }});
        }
        UserEntity user = findById(id);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return save(user);
    }

    @Override
    public UserEntity changeUsername(String id, ChangeUsernameRequest request) {
        UserEntity user = findById(id);
        findFirstByUsername(request.getUsername()).ifPresent(u -> {
            throw new UsernameAlreadyExistException(StatusEnum.VALIDATION_ERROR, new HashMap<>() {{
                put("message", "Username already exist");
            }});
        });
        user.setUsername(request.getUsername());
        return save(user);
    }

    public Optional<UserEntity> findFirstByUsername(String username) {
        return repository.findFirstByUsername(username);
    }

    @Override
    public UserEntity getMe() throws DataNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findById(userDetails.getId());
    }

    @Override
    protected JpaGenericRepository<UserEntity, String> getRepository() {
        return this.repository;
    }

    private void requestToModel(UserEntity user, UserRequest request) {
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        user.setImage(request.getImageId() == null ? null : fileService.findById(request.getImageId()));
    }

    private void check(UserRequest request) {
        if (findFirstByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(StatusEnum.USERNAME_ALREADY_EXIST,
                    new HashMap<>() {{
                        put("username", request.getUsername());
                    }});
        }

        if (repository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new BadRequestException(StatusEnum.EMAIL_ALREADY_EXIST,
                    new HashMap<>() {{
                        put("email", request.getEmail());
                    }});
        }

    }
}
