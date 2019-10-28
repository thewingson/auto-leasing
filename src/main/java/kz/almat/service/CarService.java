package kz.almat.service;

import kz.almat.model.Car;
import kz.almat.model.dto.CarDTO;

import java.sql.SQLException;
import java.util.List;

public interface CarService extends CommonService<Car>{

    void rent(Long carId, String username) throws SQLException;

    List<CarDTO> getAllDTO() throws SQLException;

    CarDTO getByIdDTO(Long carId) throws SQLException;
}
