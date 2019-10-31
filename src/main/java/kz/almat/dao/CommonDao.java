package kz.almat.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CommonDao<E> {

    List<E> getList(Connection connection);

    E getById(Connection connection, Long id);

    boolean create(Connection connection, E entity);

    boolean update(Connection connection, Long id, E entity);

    boolean delete(Connection connection, Long id);

}
