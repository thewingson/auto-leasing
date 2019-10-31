package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.CarDao;
import kz.almat.model.Car;
import kz.almat.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final String CAR = "car";
    private static final String ALL_COLUMNS_CREATE = "(mark,model,registered_number)";
    private static final String ALL_COLUMNS_UPDATE = "mark = ?, model= ?, registered_number =?";
    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?)";

    private static final String ID_EQUALS = "id = ?";
    private static final String RENTOR_EQUALS = "rentor_id =?";

    private static final String SELECT_ALL = "select c.id, c.mark, c.model, c.registered_number, u.id as rentor_id, u.username" +
            " from car c " +
            " left join user u on u.id = c.rentor_id";
    private static final String SELECT_BY_ID = SELECT_ALL + " where c.id = ?";


    private static final String INSERT_CAR_SQL = String.format(CommonQueryScripts.INSERT, CAR, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE_CAR_BY_ID = String.format(CommonQueryScripts.DELETE_BY_COLUMN, CAR, ID_EQUALS);
    private static final String UPDATE_CAR = String.format(CommonQueryScripts.UPDATE, CAR, ALL_COLUMNS_UPDATE, ID_EQUALS);

    private static final String UPDATE_RENTOR = String.format(CommonQueryScripts.UPDATE, CAR, RENTOR_EQUALS, ID_EQUALS);

    public CarDaoImpl() {
    }

    public List<Car> getList(Connection connection)  {
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                cars.add(build(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public Car getById(Connection connection, Long id) {
        Car car = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)){
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()) {
                    car = build(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }

    public boolean create(Connection connection, Car car) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_CAR_SQL)){
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Connection connection, Long id, Car car) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR)){
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());
            statement.setLong(4, id);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Connection connection, Long id) {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID)){
            statement.setLong(1, id);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Car build(ResultSet rs) {
        Long carId = null;
        String mark = null;
        String model = null;
        String registeredNumber = null;
        Long rentor_id = null;
        String username = null;

        try {
            carId = rs.getLong("id");
            mark = rs.getString("mark");
            model = rs.getString("model");
            registeredNumber = rs.getString("registered_number");
            rentor_id = rs.getLong("rentor_id");
            username = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Car(carId, mark, model, registeredNumber, new User(rentor_id, null, null, null, username, null));
    }

    public boolean rent(Connection connection, Long carId, Long userId) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_RENTOR)){
            statement.setLong(1, userId);
            statement.setLong(2, carId);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean return_(Connection connection, Long carId, Long userId){

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_RENTOR)){
            statement.setNull(1, Types.INTEGER);
            statement.setLong(2, carId);

            return (1 == statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
