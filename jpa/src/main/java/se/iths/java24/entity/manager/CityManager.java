package se.iths.java24.entity.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import se.iths.java24.entity.City;
import se.iths.java24.entity.Country;

import java.util.List;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.inTransaction;

public class CityManager {

//    private static final Scanner scanner = new Scanner(System.in);

    public static void cityMenu(EntityManager em, Scanner scanner) {
        System.out.println("~Stadsmenyn~");
        boolean back = false;

        while (!back) {
            System.out.println("""
            1 - Gå tillbaka...
            2 - Visa alla städer
            3 - Lägg till en stad
            4 - Uppdatera en stad
            5 - Ta bort en stad
            """);

            String cityChoice = scanner.nextLine();
            switch (cityChoice) {
                case "1" -> back = true;
                case "2" -> showAllCities(em);
                case "3" -> addCity(em, scanner);
                case "4" -> updateCity(em, scanner);
                case "5" -> deleteCity(em, scanner);
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }

    public static void showAllCities(EntityManager em) {
        var query = em.createQuery("SELECT c FROM City c", City.class);
        var cities = query.getResultList();
        cities.forEach(city -> System.out.println(city.getId() + " - " + city.getCityName() + ", " + city.getCountry()));
    }

    public static void addCity(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in namnet på staden");
        String cityName = scanner.nextLine();
        System.out.println("Skriv in namnet på landet");
        String countryName = scanner.nextLine();
        inTransaction(entityManager -> {
            City city = new City();
            Country country = findOrCreateCountry(entityManager, countryName);
            city.setCountry(country);
            entityManager.persist(city);
        });
        System.out.println(cityName + countryName + " har lagts till.");
    }

    public static void updateCity(EntityManager em, Scanner scanner) {
        System.out.print("Ange stadens ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine(); // Rensa bufferten
        System.out.print("Ange nytt namn: ");
        String newName = scanner.nextLine();
        System.out.print("Ange nytt land: ");
        String newCountry = scanner.nextLine();

        inTransaction(entityManager -> {
            City city = entityManager.find(City.class, cityId);
            if (city != null) {
                Country country = findOrCreateCountry(entityManager, newCountry);
                city.setCityName(newName);
                city.setCountry(country);
                System.out.println("Staden med ID " + cityId + " har uppdaterats.");
            } else {
                System.out.println("Ingen stad hittades med ID " + cityId);
            }
        });
    }

    public static void deleteCity(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in cityId: ");
        Long cityId = scanner.nextLong();
        inTransaction(entityManager -> {
            City city = entityManager.find(City.class, cityId);
            if (city != null) {
                entityManager.remove(city);
                System.out.println("Staden med ID " + cityId + " har tagits bort.");
                scanner.nextLine(); // Rensa bufferten
            } else {
                System.out.println("Ingen stad hittades med ID " + cityId);
            }

        });
    }

    private static Country findOrCreateCountry(EntityManager em, String countryName) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.countryName = :name", Country.class);
        query.setParameter("name", countryName);
        List<Country> countries = query.getResultList();
        if (!countries.isEmpty()) {
            return countries.get(0);
        } else {
            Country country = new Country();
            country.setCountryName(countryName);
            inTransaction(entityManager -> {
                entityManager.persist(country);
            });
            return country;
        }
    }
}
