package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;

@Embeddable
public class HouseAddress {

    @Basic
    private String street;
    @Basic
    private long houseNumber;

    public HouseAddress() {}

    public HouseAddress(String street, long houseNumber) {
        setStreet(street);
        setHouseNumber(houseNumber);
    }

    public HouseAddress(HouseAddress houseAddress) {
        this(houseAddress.getStreet(), houseAddress.getHouseNumber());
    }

    // Getters
    public String getStreet() {
        return street;
    }

    public long getHouseNumber() {
        return houseNumber;
    }

    // Setters
    public void setStreet(String street) {
        if (street == null || street.trim().equals("")){
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        this.street = street;
    }

    public void setHouseNumber(long houseNumber) {
        if (houseNumber <= 0){
            throw new IllegalArgumentException("House number must be higher than zero.");
        }

        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return street + " / " + houseNumber;
    }
}
