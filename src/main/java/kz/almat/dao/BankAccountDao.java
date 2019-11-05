package kz.almat.dao;

import kz.almat.model.BankAccount;
import kz.almat.model.User;

import java.sql.Connection;

public interface BankAccountDao extends CommonDao<BankAccount> {

    BankAccount getByOwnerId(Connection connection, User owner);

    boolean updateByOwner(Connection connection, BankAccount bankAccount);

}
