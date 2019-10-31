package kz.almat.dao;

import kz.almat.model.Car;

import java.sql.Connection;
import java.sql.SQLException;

public interface CarDao extends CommonDao<Car> {

    boolean rent(Connection connection, Long carId, Long userId);

    boolean return_(Connection connection, Long carId, Long userId);

}
