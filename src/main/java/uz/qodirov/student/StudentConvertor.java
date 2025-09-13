package uz.qodirov.student;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;
import uz.qodirov.user.UserEntity;

@Component
public class StudentConvertor extends Converter<StudentDto, UserEntity> {
    public StudentConvertor() {
        super(dto -> new UserEntity(),
                entity -> new StudentDto(
                        entity.getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getMiddleName(),
                        entity.getEmail(),
                        entity.getUsername(),
                        entity.getPhone()
                ));
    }
}
