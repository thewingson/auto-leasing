package kz.almat.service.impl;

import kz.almat.dao.impl.AgreementDaoImpl;
import kz.almat.model.Agreement;
import kz.almat.model.Car;
import kz.almat.service.AgreementService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AgreementServiceImpl implements AgreementService {

    private static final Logger log = Logger.getLogger(AgreementServiceImpl.class);

    private AgreementDaoImpl agreementDaoImpl;

    public AgreementServiceImpl() {
        this.agreementDaoImpl = new AgreementDaoImpl();
    }

    @Override
    public List<Agreement> getAll() {
        return null;
    }

    @Override
    public Agreement getById(Long id) {
        return null;
    }

    @Override
    public void create(Agreement entity) {

    }

    @Override
    public void update(Long id, Agreement entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Agreement getByCar(Car car) {
        try (Connection connection = HikariConnectionPool.getConnection()) {
            return agreementDaoImpl.getByCar(connection, car.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
