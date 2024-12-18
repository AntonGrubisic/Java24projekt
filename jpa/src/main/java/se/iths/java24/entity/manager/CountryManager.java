package se.iths.java24.entity.manager;
import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Country;

import java.util.List;
import java.util.Scanner;

public class CountryManager {

//    public CountryManager(EntityManager em, Scanner scanner) {
//        CountryManager.em = em;
//        this.scanner = scanner;
//    }

    public static void countryMenu(EntityManager em, Scanner scanner) {
        boolean back = false;

        while (!back) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> back = true;
                case "2" -> showAllCountries(em);
                case "3" -> addCountry(em, scanner);
                case "4" -> updateCountry(em, scanner);
                case "5" -> deleteCountry(em, scanner);
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }
    
    private static void printMenu ()  {
        System.out.println("""
            ~Landsmenyn~
            1 - Gå tillbaka...
            2 - Visa alla länder
            3 - Lägg till ett nytt land
            4 - Uppdatera ett land
            5 - Ta bort ett land
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

        Country country = new Country();
        country.setCountryName(name);
        country.setCountryCapital(capital);
        country.setCountryPopulation(population);
        country.setLandmark(landmark);

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

        em.getTransaction().begin();
        country.setCountryName(newName);
        country.setCountryCapital(newCapital);
        country.setCountryPopulation(newPopulation);
        country.setLandmark(newLandmark);
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
        em.remove(country);
        em.getTransaction().commit();

        System.out.println("Landet har tagits bort.");
    }
}
