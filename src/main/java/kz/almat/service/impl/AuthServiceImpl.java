package kz.almat.service.impl;

import kz.almat.model.User;
import kz.almat.service.AuthService;
import kz.almat.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "select id, first_name, last_name, email, username, password from usr where username =? and password=? limit 1";

    public User authenticate(String usernameToValidate, String passwordToValidate) throws SQLException {
        User user = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);
            preparedStatement.setString(1, usernameToValidate);
            preparedStatement.setString(2, passwordToValidate);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(id, firstName, lastName, email, username, password);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return user;
    }

    public boolean validate(String username, String password) {
        return false;
    }
}
