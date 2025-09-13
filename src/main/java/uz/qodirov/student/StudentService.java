package uz.qodirov.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import uz.qodirov.user.UserEntity;

public interface StudentService {
    UserEntity getOne(String id);

    Page<UserEntity> findAll(Specification<UserEntity> specification, PageRequest pageRequest);
}
