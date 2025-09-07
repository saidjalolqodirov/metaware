package uz.qodirov.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.qodirov.exception.DataNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface JpaGenericService<T extends GenericEntity<ID>, ID> extends GenericService<T, ID> {

    <S extends T> T save(S entity);

    <S extends T> T update(S entity) throws DataNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    <S extends T> T updateWithNull(S entity) throws DataNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Page<T> findAll(Pageable pageable);

    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Specification<T> specification, Pageable pageable);
}
