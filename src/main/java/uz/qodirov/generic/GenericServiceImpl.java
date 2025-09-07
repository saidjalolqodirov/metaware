package uz.qodirov.generic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.exception.InvalidDataException;

import java.util.*;

public abstract class GenericServiceImpl<T extends GenericEntity<ID>, ID> implements GenericService<T, ID> {

    protected abstract GenericRepository<T, ID> getRepository();

    private static final Logger logger = LogManager.getLogger();

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T findById(ID id) throws DataNotFoundException {
        Optional<T> t = getRepository().findById(id);
        if (t.isEmpty()) {
            String className = this.getClass().getSimpleName().replaceAll("ServiceImpl", "");
            throw new DataNotFoundException(StatusEnum.DATA_NOT_FOUND,
                    new HashMap<String, Object>() {{
                        put("className", className);
                        put("id", id);
                    }});
        }
        logger.debug(t);
        return t.get();
    }

    @Override
    public List<T> findByIds(List<ID> ids) {
        List<T> allById = getRepository().findAllById(ids);
        logger.debug(allById);
        return allById;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entityList) {
        List<S> saveAll = getRepository().saveAll(entityList);
        String className = this.getClass().getSimpleName().replaceAll("ServiceImpl", "");
        logger.debug("{} saved size: {}", className, saveAll.size());
        return saveAll;
    }

    @Override
    public void delete(ID id) {
        logger.debug("deleted by id = {}", id);
        getRepository().deleteById(id);
    }

    @Override
    public void checkDataIds(Set<ID> dataIds, List<ID> ids, String message) throws InvalidDataException {
        List<ID> notFoundIds = new ArrayList<>();
        for (ID id : ids) {
            if (!dataIds.contains(id)) {
                notFoundIds.add(id);
            }
        }
        if (!notFoundIds.isEmpty()) {
            throw new InvalidDataException(message);
        }
    }

}
