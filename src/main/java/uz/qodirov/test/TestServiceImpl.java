package uz.qodirov.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl extends JpaGenericServiceImpl<TestsEntity, String> implements TestService {
    private final TestRepository repository;

    @Override
    protected JpaGenericRepository<TestsEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    public TestsEntity create(TestRequest request) {
        TestsEntity testEntity = toModel(request, new TestsEntity());
        return save(testEntity);
    }

    @Override
    public TestsEntity update(String id, TestRequest request) {
        TestsEntity testsEntity = getOne(id);
        toModel(request, testsEntity);
        return save(testsEntity);
    }

    @Override
    public TestsEntity getOne(String id) {
        return findById(id);
    }

    private TestsEntity toModel(TestRequest request, TestsEntity model) {
        model.setName(request.getName());
        model.setDuration(request.getDuration());
        model.setType(request.getType());
        model.setPages(request.getPages());
        return model;
    }
}
