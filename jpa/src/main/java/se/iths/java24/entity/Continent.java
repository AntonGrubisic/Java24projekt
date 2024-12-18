package se.iths.java24.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Continent", schema = "demo")
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "continentName", nullable = false)
    private String continentName;

    @Column(name = "continentCountries")
    private Integer countriesAmount;

    @Column(name = "continentSize")
    private Integer continentSize;

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
    public Integer getContinentSize() {
        return continentSize;
    }

    public void setContinentSize(Integer continentSize) {
        this.continentSize = continentSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountriesAmount() {
        return countriesAmount;
    }

    public void setCountriesAmount(Integer countriesAmount) {
        this.countriesAmount = countriesAmount;
    }


    @Override
    public String toString() {
        return "continent{" +
                "id=" + id +
                ", Continent name: " + continentName + '\'' +
                ", Continent size: " + continentSize  +
                '}';
    }
}
