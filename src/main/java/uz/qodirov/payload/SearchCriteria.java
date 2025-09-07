package uz.qodirov.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;

    @Pattern(regexp = "^(^(!=)?|^(<=)?|^(>=)?|^(=)?|^(<)?|^(>)?)$")
    private String operation;

    private Object value;
}
