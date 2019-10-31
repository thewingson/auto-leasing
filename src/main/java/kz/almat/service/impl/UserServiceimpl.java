package kz.almat.service.impl;

import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.User;
import kz.almat.service.UserService;
import kz.almat.util.HikariConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceimpl implements UserService {

    private UserDaoImpl userDaoImpl;

    public UserServiceimpl() {
        this.userDaoImpl = new UserDaoImpl();
    }

    public List<User> getAll() {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return userDaoImpl.getList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getById(Long userId) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return userDaoImpl.getById(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void create(User user) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.create(connection, user)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Long id, User user) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.update(connection, id, user)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Long id) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.delete(connection, id)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
