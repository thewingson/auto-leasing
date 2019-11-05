package kz.almat.model;

public class BankAccount {

    private Long id;
    private String accountNumber;
    private Double balance;
    private User owner;

    public BankAccount() {
    }

    public BankAccount(Long id, String accountNumber, Double balance, User owner) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
