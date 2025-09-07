package uz.qodirov.generic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.qodirov.exception.DataNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class JpaGenericServiceImpl<T extends GenericEntity<ID>, ID>
        extends GenericServiceImpl<T, ID>
        implements JpaGenericService<T, ID> {
    private static final Logger logger = LogManager.getLogger();

    protected abstract JpaGenericRepository<T, ID> getRepository();

    @Override
    public <S extends T> T save(S entity) {
        S s = getRepository().saveAndFlush(entity);
        logger.debug(s);
        return s;
    }

    @Override
    public <S extends T> T update(S entity) {
        S s = getRepository().saveAndFlush(entity);
        logger.debug(s);
        return s;
    }

    @Override
    public <S extends T> T updateWithNull(S entity) throws DataNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T oldEntity = findById(entity.getId());
        if (oldEntity.isDeleted()) {
            throw new DataNotFoundException();
        }
        return save(oldEntity.mergeWithNull(entity));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Page<T> all = getRepository().findAll(pageable);
        logger.debug(all);
        return all;
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        List<T> all = getRepository().findAll(specification);
        logger.debug(all);
        return all;
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        Page<T> all = getRepository().findAll(specification, pageable);
        logger.debug(all);
        return all;
    }
}
