package kz.almat.service;

import kz.almat.model.User;

import java.sql.SQLException;

public interface AuthService {

    public User authenticate(String username, String password) throws SQLException;

    public boolean validate(String username, String password);

}
