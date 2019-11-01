package kz.almat.service.impl;

import kz.almat.dao.impl.CarDaoImpl;
import kz.almat.dao.impl.UserDaoImpl;
import kz.almat.model.Car;
import kz.almat.model.User;
import kz.almat.model.dto.CarDTO;
import kz.almat.service.CarService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private CarDaoImpl carDaoImpl;

    private UserDaoImpl userDaoImpl;

    public CarServiceImpl() {
        this.carDaoImpl = new CarDaoImpl();
        this.userDaoImpl = new UserDaoImpl();
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = null;

        try (Connection connection = HikariConnectionPool.getConnection()) {
            cars = carDaoImpl.getList(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return cars;
    }

    @Override
    public List<CarDTO> getAllDTO() {

        List<Car> cars = null;

        try (Connection connection = HikariConnectionPool.getConnection()) {
            cars = carDaoImpl.getList(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        List<CarDTO> carDTOS = new ArrayList<>();

        if(cars != null){
            for (Car c : cars) {
                CarDTO carDTO = new CarDTO(c.getId(), c.getMark(), c.getModel(), c.getRegisteredNumber(), c.getRentor().getId());
                carDTOS.add(carDTO);
            }
        }

        return carDTOS;

    }

    @Override
    public Car getById(Long carId) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            return carDaoImpl.getById(connection, carId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public CarDTO getByIdDTO(Long carId) {
        Car car = null;
        try (Connection connection = HikariConnectionPool.getConnection()) {
            car = carDaoImpl.getById(connection, carId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (car != null){
            return new CarDTO(car.getId(), car.getMark(), car.getModel(), car.getRegisteredNumber(), car.getRentor().getId());
        } else {
            return null;
        }

    }

    @Override
    public void create(Car car) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (carDaoImpl.create(connection, car)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void update(Long id, Car car) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (carDaoImpl.update(connection, id, car)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void delete(Long id) {

        try (Connection connection = HikariConnectionPool.getConnection()) {
            if (carDaoImpl.delete(connection, id)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void rent(Long carId, String username) {


        try (Connection connection = HikariConnectionPool.getConnection()) {
            User user = userDaoImpl.getByUsername(connection, username);

            if (carDaoImpl.rent(connection, carId, user.getId())) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void returnBack(Long carId, Long userId) {

        try (Connection connection = HikariConnectionPool.getConnection()) {

            if (carDaoImpl.returnBack(connection, carId, userId)) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
