package kz.almat.service;

import java.util.List;

public interface CommonService<E> {

    List<E> getAll();

    E getById(Long id);

    void create(E entity);

    void update(Long id, E entity);

    void delete(Long id);

}
