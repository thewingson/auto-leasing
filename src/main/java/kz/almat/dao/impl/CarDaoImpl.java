package kz.almat.dao.impl;

import kz.almat.constant.CommonQueryScripts;
import kz.almat.dao.CarDao;
import kz.almat.model.Car;
import kz.almat.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final String CAR = "car";
    private static final String ALL_COLUMNS = "id,mark,model,registered_number";
    private static final String ALL_COLUMNS_CREATE = "(mark,model,registered_number)";
    private static final String ALL_COLUMNS_UPDATE = "mark = ?, model= ?, registered_number =?";
    private static final String STATEMENT_VALUES_CREATE = "(?, ?, ?)";

    private static final String SELECT_JOIN_USER = "select c.id, c.mark, c.model, c.registered_number, u.id as rentor_id, u.username" +
                                                    " from car c " +
                                                    " inner join usr u on u.id = c.rentor_id";

    private static final String SELECT_BY_ID_JOIN_USER = SELECT_JOIN_USER + " where c.id = ?";

    private static final String RENTOR_UPDATE = "rentor_id =?";

    private static final String SELECT_ALL_CARS = String.format(CommonQueryScripts.SELECT_ALL, CAR);
    private static final String SELECT_CAR_BY_ID = String.format(CommonQueryScripts.SELECT_BY_ID, ALL_COLUMNS, CAR);
    private static final String INSERT_CAR_SQL = String.format(CommonQueryScripts.INSERT, CAR, ALL_COLUMNS_CREATE, STATEMENT_VALUES_CREATE);
    private static final String DELETE_CAR_BY_ID = String.format(CommonQueryScripts.DELETE_BY_ID, CAR);
    private static final String UPDATE_CAR = String.format(CommonQueryScripts.UPDATE, CAR, ALL_COLUMNS_UPDATE);

    private static final String UPDATE_RENTOR = String.format(CommonQueryScripts.UPDATE, CAR, RENTOR_UPDATE);

    public CarDaoImpl() {
    }

    public List<Car> getList(Connection connection) throws SQLException {
        List<Car> cars = new ArrayList<Car>();

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_JOIN_USER);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            cars.add(build(rs));
        }

        return cars;
    }

    public Car getById(Connection connection, Long id) throws SQLException {
        Car car = null;
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_JOIN_USER);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            car = build(rs);
        }

        return car;
    }

    public boolean create(Connection connection, Car car) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_CAR_SQL);
        statement.setString(1, car.getMark());
        statement.setString(2, car.getModel());
        statement.setString(3, car.getRegisteredNumber());

        return (1 == statement.executeUpdate());
    }

    public boolean update(Connection connection, Long id, Car car) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_CAR);
        statement.setString(1, car.getMark());
        statement.setString(2, car.getModel());
        statement.setString(3, car.getRegisteredNumber());
        statement.setLong(4, id);

        return (1 == statement.executeUpdate());
    }

    public boolean delete(Connection connection, Long id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID);
        statement.setLong(1, id);

        return (1 == statement.executeUpdate());
    }

    private Car build(ResultSet rs){
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

        return new Car(carId, mark, model, registeredNumber, new User(rentor_id,null,null,null,username, null));
    }

    public boolean rent(Connection connection, Long carId, Long userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_RENTOR);
        statement.setLong(1, userId);
        statement.setLong(2, carId);

        return (1 == statement.executeUpdate());
    }
}
