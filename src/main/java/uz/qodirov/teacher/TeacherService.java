package uz.qodirov.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import uz.qodirov.user.UserEntity;

public interface TeacherService {
    UserEntity create(TeacherRequest request);

    UserEntity update(String id, TeacherRequest request);

    Page<UserEntity> findAll(Specification<UserEntity> specification, PageRequest pageRequest);

    UserEntity getOne(String id);
}
