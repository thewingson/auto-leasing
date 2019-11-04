package kz.almat.service.impl;

import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.User;
import kz.almat.service.UserService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceimpl implements UserService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private UserDaoImpl userDaoImpl;

    public UserServiceimpl() {
        this.userDaoImpl = new UserDaoImpl();
    }

    @Override
    public List<User> getAll() {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return userDaoImpl.getList(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public User getById(Long userId) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return userDaoImpl.getById(connection, userId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public void create(User user) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.create(connection, user)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void update(Long id, User user) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.update(connection, id, user)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void delete(Long id) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (userDaoImpl.delete(connection, id)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
