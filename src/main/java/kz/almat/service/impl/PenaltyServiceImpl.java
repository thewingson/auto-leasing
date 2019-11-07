package kz.almat.service.impl;

import kz.almat.dao.impl.*;
import kz.almat.model.*;
import kz.almat.model.enums.CarState;
import kz.almat.model.enums.OperationCode;
import kz.almat.service.PenaltyService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class PenaltyServiceImpl implements PenaltyService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private static final Long COMPANY_BANK_ACCOUNT_ID = 3L;

    private PenaltyDaoImpl penaltyDaoImpl;

    private AgreementDaoImpl agreementDaoImpl;

    private CarDaoImpl carDaoImpl;

    private OperationDaoImpl operationDaoImpl;

    private BankAccountDaoImpl bankAccountDaoImpl;

    public PenaltyServiceImpl() {
        this.penaltyDaoImpl = new PenaltyDaoImpl();
        this.agreementDaoImpl = new AgreementDaoImpl();
        this.carDaoImpl = new CarDaoImpl();
        this.operationDaoImpl = new OperationDaoImpl();
        this.bankAccountDaoImpl = new BankAccountDaoImpl();
    }

    @Override
    public List<Penalty> getAll() {
        return null;
    }

    @Override
    public Penalty getById(Long id) {
        return null;
    }

    @Override
    public void create(Penalty entity) {

    }

    @Override
    public void update(Long id, Penalty entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Penalty> getByDebtor(Long userId) {
        List<Penalty> penalties = null;

        try (Connection connection = HikariConnectionPool.getConnection()) {

            List<Agreement> agreements = agreementDaoImpl.getByRentor(connection, userId);

            penalties = penaltyDaoImpl.getByDebtor(connection, agreements.get(0).getRentor().getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return penalties;
    }

    @Override
    public void pay(Long id, Long userId) {
        try (Connection connection = HikariConnectionPool.getConnection()) {

            Penalty penalty = penaltyDaoImpl.getById(connection, id);

            Agreement agreement = agreementDaoImpl.getById(connection, penalty.getAgreement().getId());

            BankAccount bankAccountDebtor = bankAccountDaoImpl.getByOwnerId(connection, agreement.getRentor());
            BankAccount bankAccountCompany = bankAccountDaoImpl.getById(connection, COMPANY_BANK_ACCOUNT_ID);

            bankAccountDebtor.setBalance(bankAccountDebtor.getBalance() - penalty.getFeeAmount());
            bankAccountCompany.setBalance(bankAccountCompany.getBalance() + penalty.getFeeAmount());

            Operation operation = new Operation(null, penalty.getFeeAmount(), bankAccountDebtor, bankAccountCompany, Timestamp.valueOf(LocalDateTime.now()), OperationCode.REPAIR);

            Car car = carDaoImpl.getById(connection, agreement.getCar().getId());
            car.setCarState(CarState.FREE);

            if(agreement.getRentor().getId() == userId &&
                    bankAccountDaoImpl.update(connection, null, bankAccountDebtor) &&
                    bankAccountDaoImpl.update(connection, null, bankAccountCompany) &&
                    operationDaoImpl.create(connection, operation) &&
                    penaltyDaoImpl.delete(connection, id) &&
                    agreementDaoImpl.delete(connection, penalty.getAgreement().getId()) &&
                    carDaoImpl.update(connection, car.getId(), car)){
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
