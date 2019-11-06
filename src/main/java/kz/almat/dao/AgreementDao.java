package kz.almat.dao;

import kz.almat.model.Agreement;

import java.sql.Connection;

public interface AgreementDao extends CommonDao<Agreement> {

    boolean deleteByCar(Connection connection, Long carId);

    Agreement getByCar(Connection connection, Long carId);
}
