package kz.almat.model;

public class Penalty {

    private Long id;
    private User debtor;
    private Double feeAmount;
    private String description;

    public Penalty() {
    }

    public Penalty(Long id, User debtor, Double feeAmount, String description) {
        this.id = id;
        this.debtor = debtor;
        this.feeAmount = feeAmount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
