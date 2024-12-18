package se.iths.java24.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    private String countryName;
    private String countryCapital;
    private Integer countryPopulation;
    private String landmark;

    // Getters and Setters
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public Integer getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(Integer countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "Land: " + countryName +
                ", Huvudstad: " + countryCapital +
                ", Befolkning: " + countryPopulation +
                ", Landmärke: " + landmark;
    }


}
