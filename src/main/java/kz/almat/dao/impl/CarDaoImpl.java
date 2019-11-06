package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.CarDao;
import kz.almat.model.Car;
import kz.almat.model.CarCategory;
import kz.almat.model.User;
import kz.almat.model.enums.CarState;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final Logger log = Logger.getLogger(CarDaoImpl.class);

    // where
    private static final String WHERE = " where ";

    //equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String CATEGORY_ID_EQUALS = " category_id = ? ";

    private static final String CAR = "car";
    private static final String ALL_COLUMNS_UPDATE = "mark = ?, model= ?, registered_number =?";

    private static final String RENTOR_EQUALS = "rentor_id =?";

    // selects
    private static final String SELECT_ALL = " select c.id, c.mark, c.model, c.registered_number, c.category_id, cs.name as state from car c " +
            " join car_state cs on cs.id = c.state_id";
    private static final String SELECT_ALL_FREE = SELECT_ALL + WHERE + " cs.name = 'FREE'";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_RENTOR = SELECT_ALL + " inner join agreement a on a.car_id = c.id " +
            " inner join user u on u.id = a.user_id" +
            " where u.id = ?";

    // insert
    private static final String INSERT = "insert into car(mark, model, registered_number, category_id, state_id) " +
            " values(?, ?, ?, ?, (select cs.id from car_state cs where cs.name = ?))";

    // delete
    private static final String DELETE = " delete from car ";
    private static final String DELETE_BY_ID = DELETE + WHERE + ID_EQUALS;

    // update
    private static final String UPDATE = " update car set mark = ?, model= ?, registered_number = ?, category_id = ?, " +
            " state_id = (select cs.id from car_state cs where cs.name = ?) ";
    private static final String UPDATE_BY_ID = UPDATE + WHERE + ID_EQUALS;
    private static final String UPDATE_RENTOR = String.format(CommonQueryScripts.UPDATE, CAR, RENTOR_EQUALS, ID_EQUALS);

    public CarDaoImpl() {
    }

    @Override
    public List<Car> getList(Connection connection) {
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FREE);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                cars.add(build(rs));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return cars;
    }

    @Override
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

    @Override
    public List<Car> getByRentor(Connection connection, User rentor) {
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_RENTOR)){
             preparedStatement.setLong(1, rentor.getId());
              try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    cars.add(build(rs));
                }
              }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return cars;
    }

    @Override
    public boolean create(Connection connection, Car car) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());
            statement.setLong(4, car.getCategory().getId());
            statement.setString(5, CarState.FREE.toString());

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Connection connection, Long id, Car car) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());
            statement.setLong(4, car.getCategory().getId());
            statement.setString(5, car.getCarState().toString());
            statement.setLong(6, id);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

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

    private Car build(ResultSet rs) {
        Long carId = null;
        String mark = null;
        String model = null;
        String registeredNumber = null;
        Long categoryId = null;
        String state = null;

        try {
            carId = rs.getLong("id");
            mark = rs.getString("mark");
            model = rs.getString("model");
            registeredNumber = rs.getString("registered_number");
            categoryId = rs.getLong("category_id");
            state = rs.getString("state");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        CarCategory carCategory = new CarCategory();
        carCategory.setId(categoryId);

        return new Car(carId, mark, model, registeredNumber, carCategory, CarState.valueOf(state));
    }

    @Override
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
