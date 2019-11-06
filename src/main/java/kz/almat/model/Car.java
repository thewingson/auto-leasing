package kz.almat.model;

import kz.almat.model.enums.CarState;

public class Car {

    private Long id;
    private String mark;
    private String model;
    private String registeredNumber;
    private CarCategory category;
    private CarState carState;

    public Car() {
    }

    public Car(Long id, String mark, String model, String registeredNumber, CarCategory category, CarState carState) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.registeredNumber = registeredNumber;
        this.category = category;
        this.carState = carState;
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

    public CarState getCarState() {
        return carState;
    }

    public void setCarState(CarState carState) {
        this.carState = carState;
    }
}
