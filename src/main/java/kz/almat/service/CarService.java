package kz.almat.service;

import kz.almat.model.Agreement;
import kz.almat.model.Car;
import kz.almat.model.dto.CarDTO;
import kz.almat.model.enums.CarState;

import java.util.List;

public interface CarService extends CommonService<Car> {

    void rent(Agreement agreement, String driverLicense);

    void returnBack(Long carId, Long userId);

    List<CarDTO> getAllDTO();

    CarDTO getByIdDTO(Long carId);

    List<Car> getByRentor(Long userId);

    List<Car> getByState(CarState aReturn);

    void acceptReturn(Long id);
}
