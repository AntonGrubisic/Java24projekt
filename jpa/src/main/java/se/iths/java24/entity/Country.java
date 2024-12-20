package se.iths.java24.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    private String countryName;
    private String countryCapital;
    private Integer countryPopulation;
    private String landmark;
    private int continentId;

    // Getters and Setters
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
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
    public int getContinentId () {
        return continentId;
    }
    public void setContinentId (int continentId) {
        this.continentId = continentId;
    }

    @Override
    public String toString() {
        return "Land: " + countryName +
                ", Huvudstad: " + countryCapital +
                ", Befolkning: " + countryPopulation +
                ", Landmärke: " + landmark;
    }


}
