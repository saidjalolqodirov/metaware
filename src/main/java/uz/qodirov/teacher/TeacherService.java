package uz.qodirov.teacher;

import uz.qodirov.user.UserEntity;

public interface TeacherService {
    UserEntity create(TeacherRequest request);

    UserEntity update(String id, TeacherRequest request);
}
