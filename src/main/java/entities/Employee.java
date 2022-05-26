package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee extends Person {

    @Basic(optional = false)
    private String profession;

    public Employee() {}

    public Employee(String firstName, String lastName, LocalDateTime dateOfBirth, String profession) {
        super (firstName, lastName,dateOfBirth);
        setProfession(profession);
    }

    @Override
    public double getRentCosts() {
        return 0;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
        if (profession == null || profession.trim().equals("")) {
            throw new IllegalArgumentException("Profession cannot be null or empty.");
        }
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "Employee " + super.toString() +
                " profession='" + profession + "}\n";
    }
}
