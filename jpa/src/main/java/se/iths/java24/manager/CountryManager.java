package se.iths.java24.manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import se.iths.java24.entity.Country;

import java.util.List;
import java.util.Scanner;
import static se.iths.java24.JPAUtil.setForeignKeyChecks;

public class CountryManager {

    public static void countryMenu(EntityManager em, Scanner scanner) {
        boolean back = false;

        while (!back) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAllCountries(em);
                case "2" -> addCountry(em, scanner);
                case "3" -> updateCountry(em, scanner);
                case "4" -> deleteCountry(em, scanner);
                case "5" -> back = true;
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                ~Landsmeny~
                1 - Visa alla länder
                2 - Lägg till ett nytt land
                3 - Uppdatera ett land
                4 - Ta bort ett land
                5 - Gå tillbaka...
                """);
    }

    public static void showAllCountries(EntityManager em) {
        List<Country> countries = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
        if (countries.isEmpty()) {
            System.out.println("Inga länder hittades i databasen.");
        } else {
            countries.forEach(System.out::println);
        }
    }

    private static void addCountry(EntityManager em, Scanner scanner) {
        System.out.print("Ange landets namn: ");
        String name = scanner.nextLine();
        System.out.print("Ange huvudstad: ");
        String capital = scanner.nextLine();
        System.out.print("Ange befolkning: ");
        int population = Integer.parseInt(scanner.nextLine());
        System.out.print("Ange landmärke: ");
        String landmark = scanner.nextLine();
        System.out.println("""
                Europa (1)
                Antarktis (2)
                Asien (3)
                Oceanien (4)
                Afrika (5)
                Nordamerika (6)
                Sydamerika (7)
                """);
        System.out.println("Ange kontinent-ID för landet: ");
        int continentId = Integer.parseInt(scanner.nextLine());

        Country country = new Country();
        country.setCountryName(name);
        country.setCountryCapital(capital);
        country.setCountryPopulation(population);
        country.setLandmark(landmark);
        country.setContinentId(continentId);

        em.getTransaction().begin();
        em.persist(country);
        em.getTransaction().commit();

        System.out.println("Landet har lagts till.");
    }

    private static void updateCountry(EntityManager em, Scanner scanner) {
        System.out.print("Ange ID för landet du vill uppdatera: ");
        Long countryId = Long.parseLong(scanner.nextLine());

        Country country = em.find(Country.class, countryId);
        if (country == null) {
            System.out.println("Inget land hittades med det angivna ID.");
            return;
        }

        System.out.print("Ange nytt namn för landet (nuvarande: " + country.getCountryName() + "): ");
        String newName = scanner.nextLine();
        System.out.print("Ange ny huvudstad (nuvarande: " + country.getCountryCapital() + "): ");
        String newCapital = scanner.nextLine();
        System.out.print("Ange ny befolkning (nuvarande: " + country.getCountryPopulation() + "): ");
        int newPopulation = Integer.parseInt(scanner.nextLine());
        System.out.print("Ange nytt landmärke (nuvarande: " + country.getLandmark() + "): ");
        String newLandmark = scanner.nextLine();
        System.out.println("""
                Europa (1)
                Antarktis (2)
                Asien (3)
                Oceanien (4)
                Afrika (5)
                Nordamerika (6)
                Sydamerika (7)
                """);
        System.out.println("Ange kontinent-ID för landet: (nuvarande: " + country.getContinentId() + ")");
        int continentId = Integer.parseInt(scanner.nextLine());

        em.getTransaction().begin();
        country.setCountryName(newName);
        country.setCountryCapital(newCapital);
        country.setCountryPopulation(newPopulation);
        country.setLandmark(newLandmark);
        country.setContinentId(continentId);
        em.getTransaction().commit();

        System.out.println("Landet har uppdaterats.");
    }

    private static void deleteCountry(EntityManager em, Scanner scanner) {
        System.out.print("Ange ID för landet du vill ta bort: ");
        Long countryId = Long.parseLong(scanner.nextLine());

        Country country = em.find(Country.class, countryId);
        if (country == null) {
            System.out.println("Inget land hittades med det angivna ID.");
            return;
        }

        em.getTransaction().begin();
        setForeignKeyChecks(em, false);
        em.remove(country);
        setForeignKeyChecks(em, true);
        em.getTransaction().commit();

        System.out.println("Landet har tagits bort.");
    }
}
