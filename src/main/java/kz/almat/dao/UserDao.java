package kz.almat.dao;

import kz.almat.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao extends CommonDao<User> {

    User getByUsername(Connection connection, String username) throws SQLException;
}
