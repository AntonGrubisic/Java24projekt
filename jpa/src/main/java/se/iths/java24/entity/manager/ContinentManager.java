package se.iths.java24.entity.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.City;
import se.iths.java24.entity.Continent;

import java.util.Scanner;

import static se.iths.java24.JPAUtil.inTransaction;

public class ContinentManager {

    public static void continentMenu(EntityManager em, Scanner scanner) {
        System.out.println("~KontinentMeny~");
        boolean back = false;

        while (!back) {
            System.out.println("""
            1 - Gå tillbaka...
            2 - Visa alla kontinenter
            3 - Lägg till en kontinent
            4 - Uppdatera en kontinent
            5 - Ta bort en kontinent
            """);

            String continentChoice = scanner.nextLine();
            switch (continentChoice) {
                case "1" -> back = true;
                case "2" -> showAllContinents(em);
                case "3" -> addContinent(em, scanner);
                case "4" -> updateContinent(em, scanner);
                case "5" -> deleteContinent(em, scanner);
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    public static void showAllContinents(EntityManager em) {
        var query = em.createQuery("SELECT c FROM Continent c", Continent.class);
        var cities = query.getResultList();
        cities.forEach(continent -> System.out.println(continent.getId() + " - " + continent.getContinentName() + ", " + continent.getContinentName()));
    }

    public static void addContinent(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in namnet på kontinenten: ");
        String continentName = scanner.nextLine();
        Continent continent = new Continent();
        continent.setContinentName(continentName);

        inTransaction(entityManager -> {
            entityManager.persist(continent);
        });
        System.out.println(continentName + " har lagts till.");
    }

    public static void updateContinent(EntityManager em, Scanner scanner) {
        System.out.print("Ange kontinentens ID: ");
        Long continentId = scanner.nextLong();
        scanner.nextLine(); // Rensa bufferten
        System.out.print("Ange ny kontinent: ");
        String newName = scanner.nextLine();


        inTransaction(entityManager -> {
            Continent continent = entityManager.find(Continent.class, continentId);
            if (continent != null) {
                continent.setContinentName(newName);
                System.out.println("Kontinenten med ID " + continentId + " har uppdaterats.");
            } else {
                System.out.println("Ingen kontinent hittades med ID " + continentId);
            }
        });
    }

    public static void deleteContinent(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in continentId: ");
        Long continentId = scanner.nextLong();
        inTransaction(entityManager -> {
            Continent continent = entityManager.find(Continent.class, continentId);
            if (continent != null) {
                entityManager.remove(continent);
                System.out.println("Kontinenten med ID " + continentId + " har tagits bort.");
                scanner.nextLine(); // Rensa bufferten
            } else {
                System.out.println("Ingen kontinent hittades med ID " + continentId);
            }

        });
    }
}
