package kz.almat.dao.impl;

import kz.almat.dao.DriverLicenseDao;
import kz.almat.model.DriverLicense;
import kz.almat.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DriverLicenseDaoImpl implements DriverLicenseDao {

    private static final Logger log = Logger.getLogger(DriverLicenseDaoImpl.class);

    private static final String SELECT_ALL = "select dl.id, dl.license_number, dl.user_id " +
            " from driver_license dl ";
    private static final String SELECT_BY_LICENSE_NUMBER = SELECT_ALL + " where dl.license_number = ?";

    @Override
    public List<DriverLicense> getList(Connection connection) {
        return null;
    }

    @Override
    public DriverLicense getById(Connection connection, Long id) {
        return null;
    }

    public DriverLicense getByLicenseNumber(Connection connection, String licenseNumber) {
        DriverLicense driverLicense = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LICENSE_NUMBER)) {
            preparedStatement.setString(1, licenseNumber);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    driverLicense = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return driverLicense;
    }

    @Override
    public boolean create(Connection connection, DriverLicense entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Long id, DriverLicense entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {

        return  false;
    }

    private DriverLicense build(ResultSet rs) {
        Long id = null;
        String licenseNumber = null;
        Long user_id = null;

        try {
            id = rs.getLong("id");
            licenseNumber = rs.getString("license_number");
            user_id = rs.getLong("user_id");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        User user = new User();
        user.setId(user_id);

        return new DriverLicense(id, licenseNumber, user);
    }
}
