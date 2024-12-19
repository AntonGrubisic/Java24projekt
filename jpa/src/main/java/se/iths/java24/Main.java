package se.iths.java24;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.User;
import se.iths.java24.manager.ContinentManager;
import se.iths.java24.manager.EuropaQuizManager;
import se.iths.java24.manager.UserManager;
import se.iths.java24.manager.CountryManager;
import se.iths.java24.manager.StatisticsManager;

import java.util.Scanner;

import static se.iths.java24.JPAUtil.getEntityManager;
import static se.iths.java24.JPAUtil.inTransaction;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        boolean quit = false;

        System.out.println("Välkommen till GeoQuiz! 🌍");
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
                    EuropaQuizManager.EuropaQuizMenu(em, scanner);
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
                3 - Starta kontinent-Quiz
                4 - Starta Europa-Quiz
                5 - Visa statistik
                6 - Stäng applikationen
                """);
    }

    private static void startQuiz(EntityManager em) {
        int score = 0;  // Initialize the score
        long userId = 0;
        System.out.println("Välkommen till quizet!");
        System.out.println("Ange ditt userId");
        userId = scanner.nextLong();
        long finalUserId = userId;
        inTransaction(entityManager -> {
                    User user = entityManager.find(User.class, finalUserId);
                });

        String[] questions = {
                """
                Vad är huvudstaden i Albanien?
                1. Tirana
                2. Sarajevo
                3. Aten
                4. Paris
                """,

                """
                Vad är huvudstaden i Belgien?
                1. Bryssel
                2. Amsterdam
                3. Paris
                4. Berlin
                """,

                """
                Vad är huvudstaden i Frankrike?
                1. Paris
                2. Rom
                3. Berlin
                4. Madrid
                """,

                """
                Vad är befolkningen i Grekland?
                1. Vilket landmärke finns i Rom?
                2. Vad är huvudstaden i Spanien?
                3. Vad är huvudstaden i Sverige
                4. Vad är huvudstaden i Ryssland?
                """,

                """
                Vilken landmärke finns i Storbritannien?
                 1. Colosseum
                 2. Eiffeltorne
                 3. Big Ben
                 4. Frihetsgudinnan
                """,

                """
                 Vilket land är känt för Eiffeltornet?"
                 1. Frankrike
                 2. Italien
                 3. Tyskland
                 4. Spanien
                """

        };
        System.out.println();
        int[] correctAnswers = {1, 1, 1, 2, 1, 2}; // Correct answers for the questions

        // Ask each question
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            int answer = scanner.nextInt();  // Get user's answer


            // Check if the answer is correct
            if (answer == correctAnswers[i]) {
                score++;  // Increase score for correct answer
            }
        }

        // After the quiz, show the score to the user
        System.out.println("Din totala poäng är: " + score + " av " + questions.length + "! ⭐");


        int finalScore = score;
        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, finalUserId);
            if (user != null) {
                user.setUserScore(finalScore);  // Set the new score
                System.out.println("Poängen uppdaterades för: " + user.getUserName());
            } else {
                System.out.println("Ingen användare hittades.");
            }
        });
    }
}
