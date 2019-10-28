package kz.almat.model.dto;

public class CarDTO {

    private Long id;
    private String mark;
    private String model;
    private String registeredNumber;
    private Long rentor_id;

    public CarDTO() {
    }

    public CarDTO(Long id, String mark, String model, String registeredNumber, Long rentor_id) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.registeredNumber = registeredNumber;
        this.rentor_id = rentor_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegisteredNumber() {
        return registeredNumber;
    }

    public void setRegisteredNumber(String registeredNumber) {
        this.registeredNumber = registeredNumber;
    }

    public Long getRentor_id() {
        return rentor_id;
    }

    public void setRentor_id(Long rentor_id) {
        this.rentor_id = rentor_id;
    }
}
