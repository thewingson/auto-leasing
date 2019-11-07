package kz.almat.dao;

import kz.almat.model.Penalty;

import java.sql.Connection;
import java.util.List;

public interface PenaltyDao extends CommonDao<Penalty> {
    List<Penalty> getByDebtor(Connection connection, Long debtorId);
}
