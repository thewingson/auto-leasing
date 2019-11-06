package kz.almat.dao.impl;

import kz.almat.dao.AgreementDao;
import kz.almat.model.Agreement;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AgreementDaoImpl implements AgreementDao {

    private static final Logger log = Logger.getLogger(CarDaoImpl.class);

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String CAR_ID_EQUALS = " car_id = ? ";
//    private static final String SENDER_ID_EQUALS = " sender_id = ? ";
//    private static final String RECIEVER_ID_EQUALS = " reciever_id = ? ";


    // insert
    private static final String INSERT = "insert into agreement(user_id, car_id, start_date, end_date) " +
            " values(?, ?, ?, ?)";

    // delete
    private static final String DELETE = " delete from agreement ";
    private static final String DELETE_BY_ID = DELETE + WHERE + ID_EQUALS;
    private static final String DELETE_BY_CAR_ID = DELETE + WHERE + CAR_ID_EQUALS;

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

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Connection connection, Long id, Agreement entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    @Override
    public boolean deleteByCar(Connection connection, Long carId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_CAR_ID)) {
            statement.setLong(1, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}
