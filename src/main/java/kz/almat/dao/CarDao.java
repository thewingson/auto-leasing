package kz.almat.dao;

import kz.almat.model.Agreement;
import kz.almat.model.Car;

import java.sql.Connection;
import java.sql.SQLException;

public interface CarDao extends CommonDao<Car> {

    boolean returnBack(Connection connection, Long carId, Long userId);

}
