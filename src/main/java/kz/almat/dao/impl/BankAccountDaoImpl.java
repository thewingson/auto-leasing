package kz.almat.dao.impl;

import kz.almat.dao.BankAccountDao;
import kz.almat.model.BankAccount;
import kz.almat.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankAccountDaoImpl implements BankAccountDao {

    private static final Logger log = Logger.getLogger(BankAccountDaoImpl.class);

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String OWNER_ID_EQUALS = " user_id = ? ";

    // selects
    private static final String SELECT_ALL = " select * from bank_account ";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_OWNER_ID = SELECT_ALL +  WHERE + OWNER_ID_EQUALS;

    // update
    private static final String UPDATE = " update bank_account set balance = ? ";
    private static final String UPDATE_BY_ID = UPDATE + WHERE + ID_EQUALS;
    private static final String UPDATE_BY_OWNER_ID = UPDATE + WHERE + OWNER_ID_EQUALS;

    @Override
    public List<BankAccount> getList(Connection connection) {
        return null;
    }

    @Override
    public BankAccount getById(Connection connection, Long id) {
        BankAccount bankAccount = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bankAccount = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return bankAccount;
    }

    @Override
    public boolean create(Connection connection, BankAccount entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Long id, BankAccount bankAccount) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setDouble(1, bankAccount.getBalance());
            statement.setLong(2, bankAccount.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateByOwner(Connection connection, BankAccount bankAccount) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BY_OWNER_ID)) {
            statement.setDouble(1, bankAccount.getBalance());
            statement.setLong(2, bankAccount.getOwner().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    private BankAccount build(ResultSet rs) {
        Long id = null;
        String accountNumber = null;
        Double balance = null;
        Long userId = null;

        try {
            id = rs.getLong("id");
            accountNumber = rs.getString("account_number");
            balance = rs.getDouble("balance");
            userId = rs.getLong("user_id");
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        User user = new User();
        user.setId(userId);

        return new BankAccount(id, accountNumber, balance, user);
    }

    @Override
    public BankAccount getByOwnerId(Connection connection, User owner) {
        BankAccount bankAccount = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_OWNER_ID)) {
            preparedStatement.setLong(1, owner.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bankAccount = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return bankAccount;
    }

}
