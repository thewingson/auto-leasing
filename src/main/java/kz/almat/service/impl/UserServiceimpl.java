package kz.almat.service.impl;

import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.User;
import kz.almat.service.UserService;
import kz.almat.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceimpl implements UserService {

    private Connection connection;

    private UserDaoImpl userDaoImpl;

    public UserServiceimpl() {
        this.userDaoImpl = new UserDaoImpl();
    }

    public List<User> getAll() throws SQLException {

        connection = ConnectionPool.getConnection();
        List<User> users = userDaoImpl.getList(connection);

        connection.close();
        return users;
    }

    public User getById(Long userId) throws SQLException {

        connection = ConnectionPool.getConnection();
        User user = userDaoImpl.getById(connection, userId);

        connection.close();
        return user;
    }

    public void create(User user) throws SQLException {
        connection = ConnectionPool.getConnection();

        if (userDaoImpl.create(connection, user)) {
            connection.commit();

        }
        connection.close();

    }

    public void update(Long id, User user) throws SQLException {

        connection = ConnectionPool.getConnection();

        if (userDaoImpl.update(connection, id, user)) {
            connection.commit();
        }

        connection.close();

    }

    public void delete(Long id) throws SQLException {

        connection = ConnectionPool.getConnection();

        if (userDaoImpl.delete(connection, id)) {
            connection.commit();
        }

        connection.close();

    }
}
