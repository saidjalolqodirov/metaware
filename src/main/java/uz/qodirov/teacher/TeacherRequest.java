package uz.qodirov.teacher;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

@Data
public class TeacherRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    private String email;

    private String phone;

    private HashMap<String, Object> additionalInfo;
}
