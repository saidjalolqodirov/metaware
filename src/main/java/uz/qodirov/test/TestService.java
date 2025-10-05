package uz.qodirov.test;

import uz.qodirov.generic.JpaGenericService;

public interface TestService extends JpaGenericService<TestsEntity, String> {
    TestsEntity create(TestRequest request);

    TestsEntity update(String id, TestRequest request);

    TestsEntity getOne(String id);
}
