package kz.almat.service.impl;

import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.User;
import kz.almat.service.AuthService;
import kz.almat.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private Connection connection;

    private UserDaoImpl userDaoImpl;

    public AuthServiceImpl() {
        this.userDaoImpl = new UserDaoImpl();
    }

    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "select id, first_name, last_name, email, username, password from usr where username =? and password=? limit 1";

    public User authenticate(String usernameToValidate, String passwordToValidate) throws SQLException {
        User user;
        connection = ConnectionPool.getConnection();
        user = userDaoImpl.getByUsernameAndPassword(connection, usernameToValidate, passwordToValidate);

        connection.close();
        return user;
    }

    public boolean validate(String username, String password) {
        return false;
    }
}
