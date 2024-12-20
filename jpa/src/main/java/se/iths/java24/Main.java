package se.iths.java24;

import jakarta.persistence.EntityManager;
import se.iths.java24.manager.ContinentManager;
import se.iths.java24.manager.*;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.getEntityManager;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        boolean quit = false;

        System.out.println("Välkommen till världs-quizen! 🌍");
        // Display menu options

        while (!quit) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    UserManager.userMenu(em, scanner);
                    //addUser(em);
                    break;
                case "2":
                    CountryManager.countryMenu(em, scanner);
                    break;
                case "3":
                    ContinentManager.continentMenu(em, scanner);
                    break;
                case "4":
                    QuizManager.EuropaQuizMenu(em, scanner);
                    break;
                case "5":
                    StatisticsManager.statisticsMenu(em, scanner);
                    break;
                case "6":
                    scanner.close();
                    System.out.println("Stänger applikationen...");
                    quit = true;
                    break;


                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                ~Huvudmeny~ (Välj nedan)
                1 - Hantera användare
                2 - Hantera länder
                3 - Hantera kontinenter
                4 - Hantera Quiz
                5 - Visa statistik för länder
                6 - Stäng applikationen
                """);
    }
}
