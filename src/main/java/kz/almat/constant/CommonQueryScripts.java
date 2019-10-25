package kz.almat.constant;

public class CommonQueryScripts {

    public static final String INSERT = "INSERT INTO %s" +
            "  (%s) VALUES " + " (?, ?, ?, ?, ?);";
    public static final String SELECT_BY_ID = "select %s from %s where id =?";
    public static final String SELECT_ALL = "select * from %s";
    public static final String DELETE_BY_ID = "delete from %s where id = ?;";
    public static final String UPDATE = "update %s set %s where id = ?;";

}
