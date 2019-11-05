package kz.almat.dao.impl;

import kz.almat.dao.OperationDao;
import kz.almat.model.BankAccount;
import kz.almat.model.Operation;
import kz.almat.model.OperationCode;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class OperationDaoImpl implements OperationDao {

    private static final Logger log = Logger.getLogger(OperationDaoImpl.class);


    // where
    private static final String WHERE = " where ";


    //equals
    private static final String ID_EQUALS = " id = ? ";
    private static final String SENDER_ID_EQUALS = " sender_id = ? ";
    private static final String RECIEVER_ID_EQUALS = " reciever_id = ? ";

    // selects
    private static final String SELECT_ALL = " select op.id, op.sender_id, op.reciever_id, op.operation_date, oc.name as code" +
            "from operation op \n" +
            "join operation_code oc on oc.id = op.code_id ";
    private static final String SELECT_BY_ID = SELECT_ALL + WHERE + ID_EQUALS;
    private static final String SELECT_BY_SENDER_ID = SELECT_ALL + WHERE + SENDER_ID_EQUALS;
    private static final String SELECT_BY_RECIEVER_ID = SELECT_ALL + WHERE + RECIEVER_ID_EQUALS;

    // insert
    private static final String INSERT = "insert into operation(amount, sender_id, reciever_id, operation_date, code_id) " +
            " values(?, ?, ?, ?, (select oc.id from operation_code oc where oc.name = ?))";

    // delete
    private static final String DELETE = "delete from operation ";
    private static final String DELETE_BY_ID = DELETE + WHERE + ID_EQUALS;

    @Override
    public List<Operation> getList(Connection connection) {
        return null;
    }

    @Override
    public Operation getById(Connection connection, Long id) {
        Operation operation = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    operation = build(rs);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

        return operation;
    }

    @Override
    public boolean create(Connection connection, Operation operation) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setDouble(1, operation.getAmount());
            statement.setLong(2, operation.getSender().getId());
            statement.setLong(3, operation.getReciever().getId());
            statement.setTimestamp(4, operation.getDate());
            statement.setString(5, operation.getCode().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Connection connection, Long id, Operation entity) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    private Operation build(ResultSet rs) {
        Long id = null;
        Double amount = null;
        Long senderId = null;
        Long recieverId = null;
        Timestamp operationDate = null;
        String code = null;

        try {
            id = rs.getLong("id");
            amount = rs.getDouble("amount");
            senderId = rs.getLong("sender_id");
            recieverId = rs.getLong("reciever_id");
            operationDate = rs.getTimestamp("operation_date");
            code = rs.getString("code");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        BankAccount bankAccountSender = new BankAccount();
        bankAccountSender.setId(senderId);

        BankAccount bankAccountReciever = new BankAccount();
        bankAccountReciever.setId(recieverId);


        return new Operation(id, amount, bankAccountSender, bankAccountReciever, operationDate, OperationCode.valueOf(code));
    }
}
