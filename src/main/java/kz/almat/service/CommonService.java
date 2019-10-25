package kz.almat.service;

import java.sql.SQLException;
import java.util.List;

public interface CommonService<E> {

    List<E> getAll() throws SQLException;

    E getById(Long id) throws SQLException;

    void create(E entity) throws SQLException;

    void update(Long id, E entity) throws SQLException;

    void delete(Long id) throws SQLException;

}
