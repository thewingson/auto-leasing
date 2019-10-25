package kz.almat.service;

import java.sql.SQLException;
import java.util.List;

public interface CommonService<E> {

    public List<E> getAll() throws SQLException;
    public E getById(Long id) throws SQLException;
    public void create(E entity) throws SQLException;
    public void update(Long id, E entity) throws SQLException;
    public void delete(Long id) throws SQLException;

}
