package kz.almat.constant;

public final class CommonQueryScripts {

    private CommonQueryScripts(){}

    public static final String INSERT = "INSERT INTO %s" +
            "  %s VALUES %s;";
    public static final String SELECT_BY_COLUMN = "select %s from %s where %s";
    public static final String SELECT_ALL = "select * from %s";
    public static final String DELETE_BY_COLUMN = "delete from %s where %s;";
    public static final String UPDATE = "update %s set %s where %s;";

}
