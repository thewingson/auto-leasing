package kz.almat.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kz.almat.constant.DatabaseCodes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariConnectionPool {

    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource hikariDataSource;

    static {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

        hikariConfig.setDriverClassName(resourceBundle.getString(DatabaseCodes.DB_DRIVER_CLASS));
        hikariConfig.setJdbcUrl(resourceBundle.getString(DatabaseCodes.DB_URL));
        hikariConfig.setUsername(resourceBundle.getString(DatabaseCodes.DB_USERNAME));
        hikariConfig.setPassword(resourceBundle.getString(DatabaseCodes.DB_PASSWORD));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(resourceBundle.getString(DatabaseCodes.MAX_POOL_SIZE)));
        hikariConfig.setAutoCommit(Boolean.getBoolean(resourceBundle.getString(DatabaseCodes.AUTO_COMMIT)));
        hikariConfig.addDataSourceProperty("cachePrepStmts", Boolean.getBoolean(resourceBundle.getString(DatabaseCodes.CACHE_PREP)));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", Integer.parseInt(resourceBundle.getString(DatabaseCodes.CAHCE_SIZE)));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", Integer.parseInt(resourceBundle.getString(DatabaseCodes.CAHE_SQL_LIMIT)));
        hikariDataSource = new HikariDataSource(hikariConfig);

    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

}
