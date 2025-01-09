package se.iths.java24.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Continent;
import se.iths.java24.entity.Country;
import se.iths.java24.entity.Question;
import java.util.List;
import java.util.Scanner;
import static se.iths.java24.JPAUtil.inTransaction;

public class ContinentManager {

    public static void continentMenu(EntityManager em, Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("Välj ett alternativ: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addContinent(em, scanner);
                case "2" -> updateContinent(em, scanner);
                case "3" -> deleteContinent(em, scanner);
                case "4" -> showContinents(em);
                case "5" -> {
                    System.out.println("Återgår till huvudmenyn...");
                    exit = true;
                }
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    private static void showContinents(EntityManager em) {
        List<Continent> continents = em.createQuery("SELECT c FROM Continent c", Continent.class).getResultList();
        if (continents.isEmpty()) {
            System.out.println("Inga kontinenter hittades i databasen.");
        } else {
            continents.forEach(System.out::println);
        }
    }

    private static void updateContinent(EntityManager em, Scanner scanner) {
        System.out.print("Ange ID för kontinenten du vill uppdatera: ");
        Long continentId = Long.parseLong(scanner.nextLine());

        Continent continent = em.find(Continent.class, continentId);
        if (continent == null) {
            System.out.println("Ingen kontinent hittades med det angivna ID.");
            return;
        }

        System.out.print("Ange nytt namn för kontinenten (nuvarande: " + continent.getContinentName() + "): ");
        String newName = scanner.nextLine();
        System.out.print("Ange ny mängd länder (nuvarande: " + continent.getCountriesAmount() + "): ");
        int newCountryAmount = Integer.parseInt(scanner.nextLine());
        System.out.print("Ange ny yta (km²) (nuvarande: " + continent.getContinentSize() + "): ");
        int newSize = Integer.parseInt(scanner.nextLine());

        em.getTransaction().begin();
        continent.setContinentName(newName);
        continent.setCountriesAmount(newCountryAmount);
        continent.setContinentSize(newSize);
        em.getTransaction().commit();

        System.out.println("Kontinenten har uppdaterats.");
    }

    private static void addContinent(EntityManager em, Scanner scanner) {
        System.out.print("Ange kontinentens namn: ");
        String name = scanner.nextLine();
        System.out.print("Ange antal länder: ");
        int Amount = Integer.parseInt(scanner.nextLine());
        System.out.print("Ange yta (km²): ");
        int size = Integer.parseInt(scanner.nextLine());

        Continent continent = new Continent();
        continent.setContinentName(name);
        continent.setContinentSize(Amount);
        continent.setContinentSize(size);


        em.getTransaction().begin();
        em.persist(continent);
        em.getTransaction().commit();

        System.out.println("Landet har lagts till.");
    }

    private static void deleteContinent(EntityManager em, Scanner scanner) {
        System.out.print("Ange ID för kontinenten du vill ta bort: ");
        Long continentId = Long.parseLong(scanner.nextLine());

        Continent continent = em.find(Continent.class, continentId);
        if (continent == null) {
            System.out.println("Ingen kontinent hittades med det angivna ID.");
            return;
        }

        em.getTransaction().begin();
        em.remove(continent);
        em.getTransaction().commit();

        System.out.println("Kontinenten har tagits bort.");
    }

    private static void printMenu() {
        System.out.println("""
                ~Quiz-meny~
                1 - Lägg till kontinent
                2 - Uppdatera kontinent
                3 - Ta bort kontinent
                4 - Visa kontinenter
                5 - Gå tillbaka...
                """);
    }
}
