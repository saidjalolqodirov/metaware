package uz.qodirov.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String username;
    private String phone;
}
