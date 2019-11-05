package kz.almat.service.impl;

import kz.almat.dao.impl.*;
import kz.almat.model.*;
import kz.almat.model.dto.CarDTO;
import kz.almat.service.CarService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CarServiceImpl implements CarService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private static final Long COMPANY_BANK_ACCOUNT_ID = 3L;

    private CarDaoImpl carDaoImpl;

    private DriverLicenseDaoImpl driverLicenseDaoImpl;

    private AgreementDaoImpl agreementDaoImpl;

    private CarCategoryDaoImpl carCategoryDaoImpl;

    private BankAccountDaoImpl bankAccountDaoImpl;

    private OperationDaoImpl operationDaoImpl;

    public CarServiceImpl() {
        this.carDaoImpl = new CarDaoImpl();
        this.driverLicenseDaoImpl = new DriverLicenseDaoImpl();
        this.agreementDaoImpl = new AgreementDaoImpl();
        this.carCategoryDaoImpl = new CarCategoryDaoImpl();
        this.bankAccountDaoImpl = new BankAccountDaoImpl();
        this.operationDaoImpl = new OperationDaoImpl();
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

//        List<Car> cars = null;
//
//        try (Connection connection = HikariConnectionPool.getConnection()) {
//            cars = carDaoImpl.getList(connection);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//        }
//
//        List<CarDTO> carDTOS = new ArrayList<>();
//
//        if(cars != null){
//            for (Car c : cars) {
//                CarDTO carDTO = new CarDTO(c.getId(), c.getMark(), c.getModel(), c.getRegisteredNumber(), c.getRentor().getId());
//                carDTOS.add(carDTO);
//            }
//        }
//
//        return carDTOS;
        return null;

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
//        Car car = null;
//        try (Connection connection = HikariConnectionPool.getConnection()) {
//            car = carDaoImpl.getById(connection, carId);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//        }
//
//        if (car != null){
//            return new CarDTO(car.getId(), car.getMark(), car.getModel(), car.getRegisteredNumber(), car.getRentor().getId());
//        } else {
//            return null;
//        }

        return null;

    }

    @Override
    public List<Car> getByRentor(Long userId) {
        List<Car> cars = null;

        try (Connection connection = HikariConnectionPool.getConnection()) {
            User rentor = new User();
            rentor.setId(userId);
            cars = carDaoImpl.getByRentor(connection, rentor);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return cars;
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
    public void rent(Agreement agreement, String driverLicenseNumber) {

        try (Connection connection = HikariConnectionPool.getConnection()) {

            DriverLicense driverLicense = driverLicenseDaoImpl.getByLicenseNumber(connection, driverLicenseNumber);

            if (driverLicense != null && driverLicense.getOwner().getId() == agreement.getRentor().getId()) {

                CarCategory carCategory = carCategoryDaoImpl.getByCarId(connection, agreement.getCar().getId());
                BankAccount bankAccountRentor = bankAccountDaoImpl.getByOwnerId(connection, agreement.getRentor());
                BankAccount bankAccountCompany = bankAccountDaoImpl.getById(connection, COMPANY_BANK_ACCOUNT_ID);
                Operation operation = new Operation(null, carCategory.getCostPerDay(), bankAccountRentor, bankAccountCompany, Timestamp.valueOf(LocalDateTime.now()), OperationCode.RENT);

                bankAccountRentor.setBalance(bankAccountRentor.getBalance() - carCategory.getCostPerDay());
                bankAccountCompany.setBalance(bankAccountCompany.getBalance() + carCategory.getCostPerDay());

                if (bankAccountDaoImpl.update(connection, null, bankAccountRentor) &&
                        bankAccountDaoImpl.update(connection, null, bankAccountCompany) &&
                        operationDaoImpl.create(connection, operation) &&
                        agreementDaoImpl.create(connection, agreement)) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
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
