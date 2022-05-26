package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id_house", unique = true, nullable = false)
    private long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private LocalDateTime dateOfSale;

    @Embedded
    private HouseAddress houseAddress;

    @ElementCollection
    private Set<Integer> apartments;

    @ManyToOne
    private HousingComplex housingComplex;

    public House() {}

    public House(String name, LocalDateTime dateOfSale, HouseAddress houseAddress, Set<Integer> apartments, HousingComplex housingComplex) {
        setName(name);
        setDateOfSale(dateOfSale);
        setHouseAddress(houseAddress);
        setApartments(apartments);
        setHousingComplex(housingComplex);
    }

    public HousingComplex getHousingComplex() {
        return housingComplex;
    }

    public void setHousingComplex(HousingComplex housingComplex) {
        if (this.housingComplex == housingComplex) return;

        if (housingComplex == null) {
            HousingComplex tmp = this.housingComplex;
            this.housingComplex = null;
            tmp.removeHouse(this);
        } else {
            this.housingComplex = housingComplex;
            housingComplex.addHouse(this);
        }
    }


    //Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateOfSale() {
        return dateOfSale;
    }

    public HouseAddress getHouseAddress() {
        return houseAddress;
    }

    public Set<Integer> getApartments() {
        return Collections.unmodifiableSet(apartments);
    }

    //Setters

    public void setName(String name) throws IllegalArgumentException {
        if (name != null && name.trim().equals("")) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public void setDateOfSale(LocalDateTime dateOfSale) throws IllegalArgumentException {
        if (dateOfSale == null) {
            throw new IllegalArgumentException("Sale date cannot be null");
        }
        if (dateOfSale.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Date of sale cannot be set to date in future");
        }
        this.dateOfSale = dateOfSale;
    }

    public void setHouseAddress(HouseAddress houseAddress) throws IllegalArgumentException {
        if (houseAddress == null) {
            throw new IllegalArgumentException("Composition.basic.withAttribute.qualifide.House address cannot be null.");
        }
        this.houseAddress = houseAddress;
    }

    public void setApartments(Set<Integer> apartments) throws IllegalArgumentException {
        if (apartments == null || apartments.size() <= 0) {
            throw new IllegalArgumentException("Apartments set cannot be null");
        }
        boolean isNegative = apartments.stream().anyMatch(a -> a <= 0);
        if (isNegative) {
            throw new IllegalArgumentException("Apartments set cannot have non positive values");
        }
        this.apartments = new HashSet<>(apartments);
    }


    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfSale=" + dateOfSale.toLocalDate().toString() +
                ", houseAddress=" + houseAddress +
                ", apartments=" + apartments +
                ", housingComplex=" + housingComplex.getId() + "}\n";
    }
}