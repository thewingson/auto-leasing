package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.CarDao;
import kz.almat.model.Car;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final Logger log = Logger.getLogger(CarDaoImpl.class);

    private static final String CAR = "car";
    private static final String ALL_COLUMNS_CREATE = "(mark,model,registered_number)";
    private static final String ALL_COLUMNS_UPDATE = "mark = ?, model= ?, registered_number =?";
    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?)";

    private static final String ID_EQUALS = "id = ?";
    private static final String RENTOR_EQUALS = "rentor_id =?";

    private static final String SELECT_ALL = "select c.id, c.mark, c.model, c.registered_number " +
            " from car c " +
            " left join agreement a on a.car_id = c.id " +
            " where a.car_id is null";
    private static final String SELECT_BY_ID = SELECT_ALL + " and c.id = ?";


    private static final String INSERT_CAR_SQL = String.format(CommonQueryScripts.INSERT, CAR, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE_CAR_BY_ID = String.format(CommonQueryScripts.DELETE_BY_COLUMN, CAR, ID_EQUALS);
    private static final String UPDATE_CAR = String.format(CommonQueryScripts.UPDATE, CAR, ALL_COLUMNS_UPDATE, ID_EQUALS);

    private static final String UPDATE_RENTOR = String.format(CommonQueryScripts.UPDATE, CAR, RENTOR_EQUALS, ID_EQUALS);

    public CarDaoImpl() {
    }

    public List<Car> getList(Connection connection) {
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                cars.add(build(rs));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return cars;
    }

    public Car getById(Connection connection, Long id) {
        Car car = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    car = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return car;
    }

    public boolean create(Connection connection, Car car) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_CAR_SQL)) {
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean update(Connection connection, Long id, Car car) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR)) {
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());
            statement.setLong(4, id);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean delete(Connection connection, Long id) {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    private Car build(ResultSet rs) {
        Long carId = null;
        String mark = null;
        String model = null;
        String registeredNumber = null;

        try {
            carId = rs.getLong("id");
            mark = rs.getString("mark");
            model = rs.getString("model");
            registeredNumber = rs.getString("registered_number");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return new Car(carId, mark, model, registeredNumber);
    }

    public boolean returnBack(Connection connection, Long carId, Long userId) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_RENTOR)) {
            statement.setNull(1, Types.INTEGER);
            statement.setLong(2, carId);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return false;
    }
}
