package kz.almat.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CommonDao<E> {

    List<E> getList(Connection connection) throws SQLException;

    E getById(Connection connection, Long id) throws SQLException;

    boolean create(Connection connection, E entity) throws SQLException;

    boolean update(Connection connection, Long id, E entity) throws SQLException;

    boolean delete(Connection connection, Long id) throws SQLException;

}
