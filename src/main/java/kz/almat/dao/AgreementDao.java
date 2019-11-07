package kz.almat.dao;

import kz.almat.model.Agreement;

import java.sql.Connection;
import java.util.List;

public interface AgreementDao extends CommonDao<Agreement> {

    boolean deleteByCar(Connection connection, Long carId);

    Agreement getByCar(Connection connection, Long carId);

    List<Agreement> getByRentor(Connection connection, Long userId);
}
