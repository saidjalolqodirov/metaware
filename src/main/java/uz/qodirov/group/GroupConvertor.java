package uz.qodirov.group;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;
import uz.qodirov.student.StudentConvertor;

@Component
public class GroupConvertor extends Converter<GroupResponseDto, GroupEntity> {
    public GroupConvertor(StudentConvertor studentConvertor) {
        super(dto -> null,
                entity -> new GroupResponseDto(
                        entity.getId(),
                        entity.getName(),
                        studentConvertor.createFromEntities(entity.getStudents().stream().toList()),
                        entity.getCreatedDate(),
                        entity.getModifiedDate()
                )
        );
    }
}
