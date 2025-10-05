package uz.qodirov.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.qodirov.constant.TestType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private String id;
    private String name;
    private TestType type;
    private Integer duration;
    private PageInfo pages;
    private Long createdDate;
    private String createdBy;
}
