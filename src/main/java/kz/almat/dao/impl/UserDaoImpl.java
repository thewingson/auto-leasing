package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.UserDao;
import kz.almat.model.Role;
import kz.almat.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private static final String USER = "usr";
    private static final String USER_ROLE = "user_role";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String ALL_COLUMNS = "id,first_name,last_name,email,username,password";
    private static final String ALL_COLUMNS_CREATE = "(first_name,last_name,email,username,password)";
    private static final String ALL_COLUMNS_UPDATE = "first_name = ?, last_name= ?, email =?, username = ?, password = ?";
    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?, ?, ?)";

    private static final String ALL_COLUMNS_USER_ROLE_CREATE = "(user_id, role_id)";
    private static final String STATEMENT_VALUES_USER_ROLE_CREATE = "(?, ?)";

    private static final String SELECT_ALL_USERS = String.format(CommonQueryScripts.SELECT_ALL, USER);
    private static final String SELECT_USER_BY_ID = String.format(CommonQueryScripts.SELECT_BY_ID, ALL_COLUMNS, USER);
    private static final String SELECT_USER_BY_USERNAME = String.format(CommonQueryScripts.SELECT_BY_COLUMN, ALL_COLUMNS, USER, USERNAME);
    private static final String INSERT_USER_SQL = String.format(CommonQueryScripts.INSERT, USER, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE_USER_BY_ID = String.format(CommonQueryScripts.DELETE_BY_ID, USER);
    private static final String UPDATE_USER = String.format(CommonQueryScripts.UPDATE, USER, ALL_COLUMNS_UPDATE);
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD =
            "select u.id, u.first_name, u.last_name, u.email, u.username, u.password, r.name as role from usr u\n" +
                    "inner join user_role ur on ur.user_id = u.id \n" +
                    "inner join role r on r.id = ur.role_id \n" +
                    "where u.username = ? and u.password = ? limit 1";
    private static final String SELECT_MAX_ID =
            "select max(id) from usr";


    private static final String INSERT_USER_ROLE = String.format(CommonQueryScripts.INSERT, USER_ROLE, ALL_COLUMNS_USER_ROLE_CREATE, STATEMENT_VALUES_USER_ROLE_CREATE);

    public List<User> getList(Connection connection) throws SQLException {
        List<User> users = new ArrayList<User>();

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            users.add(build(rs));
        }

        return users;
    }

    public User getById(Connection connection, Long id) throws SQLException {
        User user = null;

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            user = build(rs);
        }

        return user;
    }

    public boolean create(Connection connection, User user) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getUsername());
        statement.setString(5, user.getPassword());

        statement.execute();

        statement = connection.prepareStatement(SELECT_MAX_ID);
        statement.executeQuery();
        ResultSet rs = statement.executeQuery();
        int max = 0;

        if (rs.next()) {
            max = rs.getInt("max");
        }

        statement = connection.prepareStatement(INSERT_USER_ROLE);
        statement.setLong(1, max);
        statement.setLong(2, 2);

        statement.execute();

        return true;
    }

    public boolean update(Connection connection, Long id, User user) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getUsername());
        statement.setString(5, user.getPassword());
        statement.setLong(6, id);

        return (1 == statement.executeUpdate());
    }

    public boolean delete(Connection connection, Long id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID);
        statement.setLong(1, id);

        return (1 == statement.executeUpdate());
    }

    private User build(ResultSet rs){
        Long userId = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String username = null;
        String password = null;
        String role = null;

        try {
            userId = rs.getLong("id");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            email = rs.getString("email");
            username = rs.getString("username");
            password = rs.getString("password");
            role = rs.getString("role");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new User(userId, firstName, lastName, email, username, password, Role.valueOf(role));
    }

    public User getByUsername(Connection connection, String username) throws SQLException {
        User user = null;

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            user = build(rs);
        }

        return user;
    }

    public User getByUsernameAndPassword(Connection connection, String username, String password) throws SQLException {
        User user = null;

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            user = build(rs);
        }

        return user;
    }
}