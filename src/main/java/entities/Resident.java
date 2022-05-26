package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "resident")
public class Resident extends Person {

    @Basic(optional = false)
    private int apartmentNumber;

    public Resident() {
    }

    public Resident(String firstName, String lastName, LocalDateTime dateOfBirth, int apartmentNumber) {
        super(firstName, lastName, dateOfBirth);
        setApartmentNumber(apartmentNumber);
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0) {
            throw new IllegalArgumentException("Apartment number can't be smaller or be 0");
        }
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public double getRentCosts() {
        return 0;
    }

    @Override
    public String toString() {
        return "Resident " + super.toString() +
                " apartmentNumber=" + apartmentNumber + "}\n";
    }
}
