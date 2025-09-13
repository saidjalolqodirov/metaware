package uz.qodirov.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class GroupStudentRequest {
    @NotEmpty
    private String groupId;

    @NotEmpty
    private String studentId;
}
