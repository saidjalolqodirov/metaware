package uz.qodirov.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.qodirov.constant.Role;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserService;

@Service
public class StudentServiceImpl implements StudentService {
    private final UserService userService;

    public StudentServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity getOne(String id) {
        UserEntity user = userService.findById(id);
        if (user == null || user.getRole() != Role.STUDENT) {
            throw new DataNotFoundException("Student not found");
        }
        return user;
    }

    @Override
    public Page<UserEntity> findAll(Specification<UserEntity> specification, PageRequest pageRequest) {
        return userService.findAll(specification, pageRequest);
    }
}
