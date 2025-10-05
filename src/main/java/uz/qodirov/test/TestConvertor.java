package uz.qodirov.test;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;

@Component
public class TestConvertor extends Converter<TestResponse, TestsEntity> {
    public TestConvertor() {
        super(dto -> new TestsEntity(),
                entity -> new TestResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getType(),
                        entity.getDuration(),
                        entity.getPages(),
                        entity.getCreatedDate(),
                        entity.getCreatedBy()
                ));
    }
}
