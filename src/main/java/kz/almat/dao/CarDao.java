package kz.almat.dao;

import kz.almat.model.Agreement;
import kz.almat.model.Car;
import kz.almat.model.User;
import kz.almat.model.enums.CarState;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao extends CommonDao<Car> {

    boolean returnBack(Connection connection, Long carId, Long userId);

    List<Car> getByRentor(Connection connection, User rentor);

    List<Car> getByState(Connection connection, CarState state);
}
