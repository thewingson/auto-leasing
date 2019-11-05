package kz.almat.dao.impl;

import kz.almat.dao.CarCategoryDao;
import kz.almat.model.CarCategory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CarCategoryDaoImpl implements CarCategoryDao {

    private static final Logger log = Logger.getLogger(CarCategoryDaoImpl.class);

    // where
    private static final String WHERE = " where ";

    // equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String OWNER_ID_EQUALS = " user_id = ? ";
    private static final String CAR_ID_EQUALS = " c.id = ? ";

    // joins
    private static final String JOIN_CAR = " ct join car c on c.category_id = ct.id ";

    // selects
    private static final String SELECT_ALL = " select * from category ";
    private static final String SELECT_JOIN_CAR = " select ct.id, ct.name, ct.cost from category ";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_CAR_ID = SELECT_JOIN_CAR + JOIN_CAR + WHERE + CAR_ID_EQUALS;


    @Override
    public List<CarCategory> getList(Connection connection) {
        return null;
    }

    @Override
    public CarCategory getById(Connection connection, Long id) {
        CarCategory carCategory= null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    carCategory = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return carCategory;
    }

    @Override
    public CarCategory getByCarId(Connection connection, Long carId) {
        CarCategory carCategory= null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CAR_ID)) {
            preparedStatement.setLong(1, carId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    carCategory = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return carCategory;
    }

    @Override
    public boolean create(Connection connection, CarCategory entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Long id, CarCategory entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    private CarCategory build(ResultSet rs) {
        Long id = null;
        String name = null;
        Double costPerDay = null;

        try {
            id = rs.getLong("id");
            name = rs.getString("name");
            costPerDay = rs.getDouble("cost");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return new CarCategory(id, name, costPerDay);
    }

}
