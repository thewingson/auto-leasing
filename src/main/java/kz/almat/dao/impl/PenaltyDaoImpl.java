package kz.almat.dao.impl;

import kz.almat.dao.PenaltyDao;
import kz.almat.model.Agreement;
import kz.almat.model.Penalty;
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
    private static final String INSERT = "insert into penalty(agreement_id fee_amount, description) " +
            " values(?, ?, ?)";

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " p.id = ? ";
    private static final String USER_ID_EQUALS = " a.user_id = ? ";

    // selects
    private static final String SELECT_ALL = " select p.id, p.fee_amount, p.description, p.agreement_id from penalty p ";
    private static final String JOIN_AGREEMENT = " join agreement a on a.id = p.agreement_id ";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_USER_ID = SELECT_ALL + JOIN_AGREEMENT + WHERE + USER_ID_EQUALS;

    // delete
    private static final String DELETE = " delete from penalty p ";
    private static final String DELETE_BY_ID = DELETE + WHERE + ID_EQUALS;


    @Override
    public List<Penalty> getList(Connection connection) {
        return null;
    }

    @Override
    public Penalty getById(Connection connection, Long id) {
        Penalty penalty = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    penalty = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return penalty;
    }

    @Override
    public boolean create(Connection connection, Penalty penalty) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, penalty.getAgreement().getId());
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Penalty> getByDebtor(Connection connection, Long debtorId) {
        List<Penalty> penalties = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setLong(1, debtorId);
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
        Long agreementId = null;
        Double feeAmount = null;
        String description = null;

        try {
            id = rs.getLong("id");
            agreementId = rs.getLong("agreement_id");
            feeAmount = rs.getDouble("fee_amount");
            description = rs.getString("description");

        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        Agreement agreement = new Agreement();
        agreement.setId(agreementId);

        return new Penalty(id, agreement, feeAmount, description);
    }
}
