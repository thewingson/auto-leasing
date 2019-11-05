package kz.almat.model;

import java.sql.Timestamp;

public class Operation {

    private Long id;
    private Double amount;
    private BankAccount sender;
    private BankAccount reciever;
    private Timestamp date;
    private OperationCode code;

    public Operation() {
    }

    public Operation(Long id, Double amount, BankAccount sender, BankAccount reciever, Timestamp date, OperationCode code) {
        this.id = id;
        this.amount = amount;
        this.sender = sender;
        this.reciever = reciever;
        this.date = date;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public OperationCode getCode() {
        return code;
    }

    public void setCode(OperationCode code) {
        this.code = code;
    }

    public BankAccount getSender() {
        return sender;
    }

    public void setSender(BankAccount sender) {
        this.sender = sender;
    }

    public BankAccount getReciever() {
        return reciever;
    }

    public void setReciever(BankAccount reciever) {
        this.reciever = reciever;
    }
}
