package kz.almat.model;

import java.sql.Timestamp;

public class Agreement {

    private Long id;
    private User rentor;
    private Car car;
    private Timestamp startDate;
    private Timestamp endDate;

    public Agreement(Long id, User rentor, Car car, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.rentor = rentor;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRentor() {
        return rentor;
    }

    public void setRentor(User rentor) {
        this.rentor = rentor;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
