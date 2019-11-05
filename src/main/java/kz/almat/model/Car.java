package kz.almat.model;

public class Car {

    private Long id;
    private String mark;
    private String model;
    private String registeredNumber;
    private CarCategory category;

    public Car() {
    }

    public Car(Long id, String mark, String model, String registeredNumber, CarCategory category) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.registeredNumber = registeredNumber;
        this.category = category;
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

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }
}
