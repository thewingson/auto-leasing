package kz.almat.model;

public class DriverLicense {

    private Long id;
    private String licenseNumber;
    private User owner;

    public DriverLicense(Long id, String licenseNumber, User owner) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
