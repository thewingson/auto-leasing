package kz.almat.service.impl;

import kz.almat.model.User;
import kz.almat.service.UserService;
import kz.almat.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceimpl implements UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO usr" +
            "  (first_name, last_name, email, username, password) VALUES " + " (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id, first_name, last_name, email, username, password from usr where id =? limit 1";
    private static final String SELECT_ALL_USERS = "select * from usr";
    private static final String DELETE_USER_BY_ID = "delete from usr where id = ?;";
    private static final String UPDATE_USER = "update usr set first_name = ?, last_name = ?, email = ?, username = ?, password = ? where id = ?;";

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<User>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                users.add(new User(id, firstName, lastName, email, username, password));
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return users;
    }

    public User getById(Long userId) throws SQLException {
        User user = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setLong(1, userId);
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

    public void create(User user) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());

            statement.execute();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update(Long id, User user) throws SQLException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setLong(6, id);

            statement.executeUpdate();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Long id) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID);
            statement.setLong(1, id);

            statement.executeUpdate();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
