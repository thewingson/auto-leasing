package kz.almat.model;

public class CarCategory {

    private Long id;
    private String name;
    private Double costPerDay;

    public CarCategory() {
    }

    public CarCategory(Long id, String name, Double costPerDay) {
        this.id = id;
        this.name = name;
        this.costPerDay = costPerDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }
}
