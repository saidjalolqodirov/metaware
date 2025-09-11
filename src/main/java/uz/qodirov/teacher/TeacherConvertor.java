package uz.qodirov.teacher;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;
import uz.qodirov.user.UserEntity;

@Component
public class TeacherConvertor extends Converter<TeacherDto, UserEntity> {
    public TeacherConvertor() {
        super(dto -> new UserEntity(),
                entity -> new TeacherDto(
                        entity.getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getMiddleName(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPhone(),
                        entity.getImageId(),
                        entity.getAdditionalInfo(),
                        entity.getCreatedDate(),
                        entity.getModifiedDate()
                ));
    }
}
