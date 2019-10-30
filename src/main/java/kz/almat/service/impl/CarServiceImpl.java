package kz.almat.service.impl;

import kz.almat.dao.impl.CarDaoImpl;
import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.Car;
import kz.almat.model.User;
import kz.almat.model.dto.CarDTO;
import kz.almat.service.CarService;
import kz.almat.util.HikariConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {

    private Connection connection;

    private CarDaoImpl carDaoImpl;

    private UserDaoImpl userDaoImpl;

    public CarServiceImpl() {
        this.carDaoImpl = new CarDaoImpl();
        this.userDaoImpl = new UserDaoImpl();
    }

    public List<Car> getAll() throws SQLException {

        connection = HikariConnectionPool.getConnection();
        List<Car> cars = carDaoImpl.getList(connection);
        connection.close();

//        try {
//            connection = HikariConnectionPool.getConnection();
//            cars = carDaoImpl.getList(connection);
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }

        return cars;
    }

    public List<CarDTO> getAllDTO() throws SQLException {

        connection = HikariConnectionPool.getConnection();
        List<Car> cars = carDaoImpl.getList(connection);
        connection.close();

//        try {
//            connection = HikariConnectionPool.getConnection();
//            cars = carDaoImpl.getList(connection);
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }

        List<CarDTO> carDTOS = new ArrayList<CarDTO>();

        for (Car c : cars){
            CarDTO carDTO = new CarDTO(c.getId(), c.getMark(), c.getModel(), c.getRegisteredNumber(), c.getRentor().getId());
            carDTOS.add(carDTO);
        }

        return carDTOS;
    }

    public Car getById(Long carId) throws SQLException {
        connection = HikariConnectionPool.getConnection();
        Car car = carDaoImpl.getById(connection, carId);
        connection.close();

        return car;
    }

    public CarDTO getByIdDTO(Long carId) throws SQLException {
        connection = HikariConnectionPool.getConnection();
        Car car = carDaoImpl.getById(connection, carId);
        connection.close();

        return new CarDTO(car.getId(), car.getMark(), car.getModel(), car.getRegisteredNumber(), car.getRentor().getId());
    }

    public void create(Car car) throws SQLException {

        connection = HikariConnectionPool.getConnection();
        if (carDaoImpl.create(connection, car)) {
            connection.commit();
        }

        connection.close();
    }

    public void update(Long id, Car car) throws SQLException {

        connection = HikariConnectionPool.getConnection();

        if (carDaoImpl.update(connection, id, car)) {
            connection.commit();
        }

        connection.close();

    }

    public void delete(Long id) throws SQLException {
        connection = HikariConnectionPool.getConnection();

        if (carDaoImpl.delete(connection, id)) {
            connection.commit();
        }

        connection.close();
    }

    public void rent(Long carId, String username) throws SQLException {
        connection = HikariConnectionPool.getConnection();

        User user = userDaoImpl.getByUsername(connection, username);

        if (carDaoImpl.rent(connection, carId, user.getId())) {
            connection.commit();
        }

        connection.close();
    }
}
