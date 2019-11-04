package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.AgreementDao;
import kz.almat.model.Agreement;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AgreementDaoImpl implements AgreementDao {

    private static final Logger log = Logger.getLogger(CarDaoImpl.class);

    private static final String AGREEMENT = "agreement";
    private static final String ALL_COLUMNS_CREATE = "(user_id,car_id,start_date,end_date)";
    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?, ?)";
    private static final String ID_EQUALS = "id = ?";


    private static final String INSERT = String.format(CommonQueryScripts.INSERT, AGREEMENT, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE = String.format(CommonQueryScripts.DELETE_BY_COLUMN, AGREEMENT, ID_EQUALS);

    @Override
    public List<Agreement> getList(Connection connection) {
        return null;
    }

    @Override
    public Agreement getById(Connection connection, Long id) {
        return null;
    }

    @Override
    public boolean create(Connection connection, Agreement agreement) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, agreement.getRentor().getId());
            statement.setLong(2, agreement.getCar().getId());
            statement.setTimestamp(3, agreement.getStartDate());
            statement.setTimestamp(4, agreement.getEndDate());

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Connection connection, Long id, Agreement entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {

        try (PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;

    }
}
