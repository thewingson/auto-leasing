package kz.almat.dao;

import kz.almat.model.CarCategory;

import java.sql.Connection;

public interface CarCategoryDao extends CommonDao<CarCategory> {

    CarCategory getByCarId(Connection connection, Long carId);
}
