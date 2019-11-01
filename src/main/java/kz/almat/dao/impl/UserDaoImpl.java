package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.UserDao;
import kz.almat.model.Role;
import kz.almat.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    private static final String USER = "user";
    private static final String USER_ROLE = "user_role";

    private static final String ALL_COLUMNS_CREATE = "(first_name,last_name,email,username,password)";
    private static final String ALL_COLUMNS_UPDATE = "first_name = ?, last_name= ?, email =?, username = ?, password = ?";

    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?, ?, ?)";

    private static final String ID_EQUALS = "id = ?";

    private static final String USER_ID_EQUALS = "user_id = ?";

    private static final String ALL_COLUMNS_USER_ROLE_CREATE = "(user_id, role_id)";

    private static final String SELECT_ALL_USERS = "select u.id, u.first_name, u.last_name, u.email, u.username, u.password, r.name as role from user u\n" +
            "inner join user_role ur on ur.user_id = u.id \n" +
            "inner join role r on r.id = ur.role_id \n";
    private static final String SELECT_USER_BY_ID = SELECT_ALL_USERS + " where u.id = ?";
    private static final String INSERT_USER_SQL = String.format(CommonQueryScripts.INSERT, USER, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE_USER_BY_ID = String.format(CommonQueryScripts.DELETE_BY_COLUMN, USER, ID_EQUALS);
    private static final String UPDATE_USER = String.format(CommonQueryScripts.UPDATE, USER, ALL_COLUMNS_UPDATE, ID_EQUALS);

    private static final String DELETE_USER_ROLE_BY_USER_ID = String.format(CommonQueryScripts.DELETE_BY_COLUMN, USER_ROLE, USER_ID_EQUALS);

    private static final String SELECT_USER_BY_USERNAME = SELECT_ALL_USERS + " where u.username = ?";

    private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " and u.password = ?";

    private static final String SELECT_MAX_ID =
            "(select max(id) from user)";
    private static final String STATEMENT_VALUES_USER_ROLE_CREATE = "(" + SELECT_MAX_ID + ", ?)";

    private static final String INSERT_USER_ROLE = String.format(CommonQueryScripts.INSERT, USER_ROLE, ALL_COLUMNS_USER_ROLE_CREATE, STATEMENT_VALUES_USER_ROLE_CREATE);

    public List<User> getList(Connection connection) {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    users.add(build(rs));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return users;
    }

    public User getById(Connection connection, Long id) {
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return user;
    }

    public boolean create(Connection connection, User user) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());

            statement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE)) {
            statement.setLong(1, 2);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean update(Connection connection, Long id, User user) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setLong(6, id);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean delete(Connection connection, Long id) {

        if (!deleteQuery(connection, id, DELETE_USER_ROLE_BY_USER_ID)) return false;

        return deleteQuery(connection, id, DELETE_USER_BY_ID);
    }

    private boolean deleteQuery(Connection connection, Long id, String searchId) {
        try (PreparedStatement statement = connection.prepareStatement(searchId)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private User build(ResultSet rs) {
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
            log.error(e.getMessage());
        }

        return new User(userId, firstName, lastName, email, username, password, Role.valueOf(role));
    }

    public User getByUsername(Connection connection, String username) {
        User user = null;


        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return user;

    }

    public User getByUsernameAndPassword(Connection connection, String username, String password) {
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return user;
    }
}
