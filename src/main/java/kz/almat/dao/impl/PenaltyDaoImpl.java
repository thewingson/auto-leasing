package kz.almat.dao.impl;

import kz.almat.dao.PenaltyDao;
import kz.almat.model.Penalty;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PenaltyDaoImpl implements PenaltyDao {

    private static final Logger log = Logger.getLogger(PenaltyDaoImpl.class);

    // insert
    private static final String INSERT = "insert into penalty(debtor_id, fee_amount, description) " +
            " values(?, ?, ?)";


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

}
