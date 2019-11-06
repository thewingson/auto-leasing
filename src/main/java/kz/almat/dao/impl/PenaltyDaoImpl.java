package kz.almat.dao.impl;

import kz.almat.dao.PenaltyDao;
import kz.almat.model.Penalty;
import kz.almat.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenaltyDaoImpl implements PenaltyDao {

    private static final Logger log = Logger.getLogger(PenaltyDaoImpl.class);

    // insert
    private static final String INSERT = "insert into penalty(debtor_id, fee_amount, description) " +
            " values(?, ?, ?)";

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String DEBTOR_ID_EQUALS = " debtor_id = ? ";

    // selects
    private static final String SELECT_ALL = " select * from penalty ";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_DEBTOR = SELECT_ALL + WHERE + DEBTOR_ID_EQUALS;


    @Override
    public List<Penalty> getList(Connection connection) {
        return null;
    }

    @Override
    public Penalty getById(Connection connection, Long id) {
        return null;
    }

    @Override
    public boolean create(Connection connection, Penalty penalty) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, penalty.getDebtor().getId());
            statement.setDouble(2, penalty.getFeeAmount());
            statement.setString(3, penalty.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Connection connection, Long id, Penalty entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    @Override
    public List<Penalty> getByDebtor(Connection connection, Long userId) {
        List<Penalty> penalties = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_DEBTOR)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    penalties.add(build(rs));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return penalties;
    }

    private Penalty build(ResultSet rs) {
        Long id = null;
        Long debtorId = null;
        Double feeAmount = null;
        String description = null;

        try {
            id = rs.getLong("id");
            debtorId = rs.getLong("debtor_id");
            feeAmount = rs.getDouble("fee_amount");
            description = rs.getString("description");

        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        User debtor = new User();
        debtor.setId(debtorId);

        return new Penalty(id, debtor, feeAmount, description);
    }
}
