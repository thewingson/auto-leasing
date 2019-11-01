package kz.almat.service.impl;

import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.User;
import kz.almat.service.AuthService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private UserDaoImpl userDaoImpl;

    public AuthServiceImpl() {
        this.userDaoImpl = new UserDaoImpl();
    }

    public User authenticate(String usernameToValidate, String passwordToValidate) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return userDaoImpl.getByUsernameAndPassword(connection, usernameToValidate, passwordToValidate);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
