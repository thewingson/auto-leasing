package kz.almat.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionPool{

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/mydb_auto_leasing";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "admin";

    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource hikariDataSource;

    static {

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(jdbcURL);
        hikariConfig.setUsername(jdbcUsername);
        hikariConfig.setPassword(jdbcPassword);
        hikariConfig.setAutoCommit(false);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSource = new HikariDataSource(hikariConfig);

    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

}
