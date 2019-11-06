package kz.almat.service.impl;

import kz.almat.dao.impl.PenaltyDaoImpl;
import kz.almat.model.Penalty;
import kz.almat.service.PenaltyService;
import kz.almat.util.HikariConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PenaltyServiceImpl implements PenaltyService {

    private static final Logger log = Logger.getLogger(CarServiceImpl.class);

    private PenaltyDaoImpl penaltyDaoImpl;

    public PenaltyServiceImpl() {
        this.penaltyDaoImpl = new PenaltyDaoImpl();
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
            penalties = penaltyDaoImpl.getByDebtor(connection, userId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return penalties;
    }
}
