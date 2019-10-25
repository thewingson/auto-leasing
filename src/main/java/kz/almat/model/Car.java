package kz.almat.model;

public class Car {

    private Long id;
    private String mark;
    private String model;
    private String registeredNumber;

    private User rentor;

    public Car(Long id, String mark, String model, String registeredNumber) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.registeredNumber = registeredNumber;
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

    public User getRentor() {
        return rentor;
    }

    public void setRentor(User rentor) {
        this.rentor = rentor;
    }
}
