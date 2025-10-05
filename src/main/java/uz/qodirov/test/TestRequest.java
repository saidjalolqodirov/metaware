package uz.qodirov.test;

import lombok.Data;
import uz.qodirov.constant.TestType;

@Data
public class TestRequest {
    private String name;
    private TestType type;
    private Integer duration;
    private PageInfo pages;
}
