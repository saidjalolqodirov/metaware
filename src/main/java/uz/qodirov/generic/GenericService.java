package uz.qodirov.generic;


import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.exception.InvalidDataException;

import java.util.List;
import java.util.Set;

public interface GenericService<T extends GenericEntity<ID>, ID> {
    long count();

    List<T> findAll();

    T findById(ID id) throws DataNotFoundException;

    List<T> findByIds(List<ID> ids);

    <S extends T> List<S> saveAll(Iterable<S> entityList);

    void delete(ID id);

    void checkDataIds(Set<ID> dataIds, List<ID> ids, String message) throws InvalidDataException;
}
