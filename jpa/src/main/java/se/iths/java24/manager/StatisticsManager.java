package se.iths.java24.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Country;

import java.util.Scanner;

public class StatisticsManager {

    public static void statisticsMenu(EntityManager em, Scanner scanner) {
        System.out.println("~Statistikmeny~");
        boolean back = false;

        while (!back) {
            System.out.println("""
                1 - Visa länder med population större än...
                2 - Visa totalt antal länder
                3 - Visa genomsnittlig population
                4 - Visa land med högst population
                5 - Visa land med lägst population
                6 - Gå tillbaka...
                """);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showCountriesWithPopulationGreaterThan(em, scanner);
                case "2" -> showTotalCountryCount(em);
                case "3" -> showAveragePopulation(em);
                case "4" -> showCountryWithHighestPopulation(em);
                case "5" -> showCountryWithLowestPopulation(em);
                case "6" -> back = true;
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }


    private static void showCountriesWithPopulationGreaterThan(EntityManager em, Scanner scanner) {
        System.out.print("Ange en populationsgräns: ");
        int populationThreshold = scanner.nextInt();
        scanner.nextLine();

        var query = em.createQuery(
                "SELECT c FROM Country c WHERE c.countryPopulation > :population ORDER BY c.countryPopulation DESC",
                Country.class);
        query.setParameter("population", populationThreshold);
        var countries = query.getResultList();

        if (countries.isEmpty()) {
            System.out.println("Inga länder hittades med en population större än " + populationThreshold);
        } else {
            System.out.println("Länder med population större än " + populationThreshold + ":");
            countries.forEach(country -> System.out.println(
                    "Land: " + country.getCountryName() +
                            ", Population: " + country.getCountryPopulation()
            ));
        }
    }


    private static void showTotalCountryCount(EntityManager em) {
        var query = em.createQuery("SELECT COUNT(c) FROM Country c");
        Long countryCount = (Long) query.getSingleResult();

        System.out.println("Totalt antal länder: " + countryCount);
    }


    private static void showAveragePopulation(EntityManager em) {
        var query = em.createQuery("SELECT AVG(c.countryPopulation) FROM Country c");
        Double averagePopulation = (Double) query.getSingleResult();

        System.out.println("Genomsnittlig population: " + (averagePopulation != null ? Math.round(averagePopulation) : 0));
    }


    private static void showCountryWithHighestPopulation(EntityManager em) {
        var query = em.createQuery(
                "SELECT c FROM Country c ORDER BY c.countryPopulation DESC",
                Country.class);
        query.setMaxResults(1);

        var country = query.getSingleResult();
        if (country != null) {
            System.out.println("Landet med högst population:");
            System.out.println("Land: " + country.getCountryName() + ", Population: " + country.getCountryPopulation());
        } else {
            System.out.println("Inga länder hittades.");
        }
    }


    private static void showCountryWithLowestPopulation(EntityManager em) {
        var query = em.createQuery(
                "SELECT c FROM Country c ORDER BY c.countryPopulation ASC",
                Country.class);
        query.setMaxResults(1);

        var country = query.getSingleResult();
        if (country != null) {
            System.out.println("Landet med lägst population:");
            System.out.println("Land: " + country.getCountryName() + ", Population: " + country.getCountryPopulation());
        } else {
            System.out.println("Inga länder hittades.");
        }
    }
}
