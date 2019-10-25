package kz.almat.service;

import kz.almat.model.Car;

import java.sql.SQLException;

public interface CarService extends CommonService<Car>{

    void rent(Long carId, String username) throws SQLException;

}
