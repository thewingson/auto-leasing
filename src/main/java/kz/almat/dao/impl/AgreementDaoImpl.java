package kz.almat.dao.impl;

import kz.almat.dao.AgreementDao;
import kz.almat.model.Agreement;
import kz.almat.model.Car;
import kz.almat.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgreementDaoImpl implements AgreementDao {

    private static final Logger log = Logger.getLogger(CarDaoImpl.class);

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " a.id = ? ";
    private static final String CAR_ID_EQUALS = " a.car_id = ? ";
    private static final String USER_ID_EQUALS = " a.user_id = ? ";


    // insert
    private static final String INSERT = "insert into agreement(user_id, car_id, start_date, end_date) " +
            " values(?, ?, ?, ?)";

    // delete
    private static final String DELETE = " delete from agreement a ";
    private static final String DELETE_BY_ID = DELETE + WHERE + ID_EQUALS;
    private static final String DELETE_BY_CAR_ID = DELETE + WHERE + CAR_ID_EQUALS;

    // selects
    private static final String SELECT_ALL = " select a.id, a.user_id, a.car_id, a.start_date, a.end_date, u.first_name, u.last_name from agreement a ";
    private static final String JOIN_CAR = " join car c on c.id = a.car_id ";
    private static final String JOIN_USER = " join user u on u.id = a.user_id ";
    private static final String SELECT_BY_ID = SELECT_ALL + JOIN_USER + WHERE + ID_EQUALS;
    private static final String SELECT_BY_CAR = SELECT_ALL + JOIN_USER + JOIN_CAR + WHERE + CAR_ID_EQUALS;
    private static final String SELECT_BY_USER = SELECT_ALL + JOIN_USER + WHERE + USER_ID_EQUALS;

    @Override
    public List<Agreement> getList(Connection connection) {
        return null;
    }

    @Override
    public Agreement getById(Connection connection, Long id) {
        Agreement agreement = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    agreement = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return agreement;
    }

    @Override
    public Agreement getByCar(Connection connection, Long carId) {
        Agreement agreement = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CAR)) {
            preparedStatement.setLong(1, carId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    agreement = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return agreement;
    }

    @Override
    public List<Agreement> getByRentor(Connection connection, Long userId) {
        List<Agreement> agreements = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER)){
            preparedStatement.setLong(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    agreements.add(build(rs));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return agreements;
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

    private Agreement build(ResultSet rs) {
        Long id = null;
        Long carId = null;
        Long userId = null;
        Timestamp startDate = null;
        Timestamp endDate = null;
        String firstName = null;
        String lastName = null;

        try {
            id = rs.getLong("id");
            carId = rs.getLong("car_id");
            userId = rs.getLong("user_id");
            startDate = rs.getTimestamp("start_date");
            endDate = rs.getTimestamp("end_date");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        User rentor = new User();
        rentor.setId(userId);
        rentor.setFirstName(firstName);
        rentor.setLastName(lastName);

        Car car = new Car();
        car.setId(carId);

        return new Agreement(id, rentor, car, startDate, endDate);
    }

}
