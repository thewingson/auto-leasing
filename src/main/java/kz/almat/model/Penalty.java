package kz.almat.model;

public class Penalty {

    private Long id;
    private Agreement agreement;
    private Double feeAmount;
    private String description;

    public Penalty() {
    }

    public Penalty(Long id, Agreement agreement, Double feeAmount, String description) {
        this.id = id;
        this.agreement = agreement;
        this.feeAmount = feeAmount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
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
