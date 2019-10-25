package kz.almat.service.impl;

import kz.almat.model.Car;
import kz.almat.service.CarService;
import kz.almat.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {

    private static final String INSERT_CAR_SQL = "INSERT INTO car" +
            "  (mark, model, registered_number) VALUES " + " (?, ?, ?);";
    private static final String SELECT_CAR_BY_ID = "select id,mark,model,registered_number from car where id =? limit 1";
    private static final String SELECT_ALL_CARS = "select * from car";
    private static final String DELETE_CAR_BY_ID = "delete from car where id = ?;";
    private static final String UPDATE_CAR = "update car set mark = ?, model= ?, registered_number =? where id = ?;";


    public List<Car> getAll() throws SQLException {
        List<Car> cars = new ArrayList<Car>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARS);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String mark = rs.getString("mark");
                String model = rs.getString("model");
                String registeredNumber = rs.getString("registered_number");
                cars.add(new Car(id, mark, model, registeredNumber));
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return cars;
    }

    public Car getById(Long carId) throws SQLException {
        Car car = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAR_BY_ID);
            preparedStatement.setLong(1, carId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String mark = rs.getString("mark");
                String model = rs.getString("model");
                String registeredNumber = rs.getString("registered_number");
                car = new Car(id, mark, model, registeredNumber);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return car;
    }

    public void create(Car car) throws SQLException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CAR_SQL);
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());

            statement.execute();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update(Long id, Car car) throws SQLException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CAR);
            statement.setString(1, car.getMark());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegisteredNumber());
            statement.setLong(4, id);

            statement.executeUpdate();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Long id) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID);
            statement.setLong(1, id);

            statement.executeUpdate();
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
