package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "housingComplex")
public class HousingComplex {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id_housing_complex", unique = true, nullable = false)
    private long id;

    @Basic(optional = false)
    private LocalDate openingDate;

    @OneToMany(
            mappedBy = "housingComplex",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<House> houses;

    public HousingComplex() {}

    public HousingComplex(LocalDate openingDate) {
        houses = new ArrayList<>();
        setOpeningDate(openingDate);
    }

    public void addHouse(House house) {
        if (house == null) {
            throw new IllegalArgumentException("Cannot add null house.");
        }

        if (!houses.contains(house)) {
            houses.add(house);
            house.setHousingComplex(this);
        }
    }

    public void removeHouse(House house) {
        if (house == null) {
            throw new IllegalArgumentException("Cannot remove house which does not exist.");
        }

        this.houses.remove(house);
        house.setHousingComplex(null);
    }

    public List<House> getHouses() {
        return Collections.unmodifiableList(houses);
    }

    public long getId() {
        return id;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        if (openingDate == null) {
            throw new IllegalArgumentException("Opening date cannot be null");
        }
        if (openingDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of opening cannot be set to date in future");
        }
        this.openingDate = openingDate;
    }

    @Override
    public String toString() {
        return "HousingComplex{" +
                "id=" + id +
                ", openingDate=" + openingDate +
                ", houses=" + houses.stream().mapToLong(House::getId).boxed().collect(Collectors.toCollection(ArrayList::new)).toString() +
                "}\n";
    }
}
