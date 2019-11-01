package kz.almat.service;

import kz.almat.model.User;

public interface AuthService {

    User authenticate(String username, String password);

}
