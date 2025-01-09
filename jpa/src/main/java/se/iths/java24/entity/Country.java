package se.iths.java24.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    @Column(name = "countryName", nullable = false)
    private String countryName;
    @Column(name = "countryCapital")
    private String countryCapital;
    @Column(name = "countryPopulation")
    private Integer countryPopulation;
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "continentId")
    private Integer continentId;


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
    public void setContinentId (Integer continentId) {
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
