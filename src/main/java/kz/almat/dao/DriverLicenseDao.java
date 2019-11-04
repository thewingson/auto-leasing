package kz.almat.dao;

import kz.almat.model.DriverLicense;

import java.sql.Connection;

public interface DriverLicenseDao extends CommonDao<DriverLicense> {

    DriverLicense getByLicenseNumber(Connection connection, String licenseNumber);
}
