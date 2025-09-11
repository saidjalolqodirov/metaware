package uz.qodirov.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String email;
    private String phone;
    private String imageId;
    private HashMap<String, Object> additionalInfo;
    private Long createdDate;
    private Long modifiedDate;
}
