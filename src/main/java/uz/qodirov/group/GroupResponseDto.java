package uz.qodirov.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.qodirov.student.StudentDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDto {
    private String id;
    private String name;
    private List<StudentDto> students;
    private Long createdDate;
    private Long modifiedDate;
}
