package se.iths.java24;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Country;
import se.iths.java24.entity.QuizSession;
import se.iths.java24.entity.User;
import java.sql.Connection;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.getEntityManager;
import static se.iths.java24.JPAUtil.inTransaction;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        boolean quit = false;

        System.out.println("Welcome to GeoQuiz!");
        // Display menu options

        while (!quit) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    scanner.close();
                    System.out.println("Stänger applikationen...");
                    quit = true;
                    break;
                case "1":
                    addUser(em);
                    break;
                case "2":
                    updateUser(em);
                    break;
                case "3":
                    deleteUser(em);
                    break;
                case "4":
                    showUsers(em);
                    break;
                case "5":
                    startQuiz(em);
                    break;
                case "6":
                    viewStatistics(em);
                    break;


                default:
                    System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("Välj nedan: ");
        System.out.println("""
                0 - Stäng applikationen
                1 - Lägg till användare
                2 - Uppdatera användare
                3 - Ta bort användare
                4 - Visa alla användare
                5 - Starta nytt Quiz
                6 - Visa statistik
                """);

    }

    // Adding new user to database
    public static void addUser(EntityManager em) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        //int score = 0;
        User user = new User();
        user.setUserName(name);
        user.setUserScore(0);

        inTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }

    //Update user
    public static void updateUser(EntityManager em) {
        System.out.println("Skriv in ditt id: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                System.out.println("Skriv in ditt namn: ");
                String newUserName = scanner.nextLine();
                user.setUserName(newUserName);

                // Om entiteten är detached kopplas den tillbaka
                if (!entityManager.contains(user)) {
                    user = entityManager.merge(user);
                };

                System.out.println("Användaren har uppdaterats");
            } else
                System.out.println("Ingen användare hittades med ID: " + userId);
        });
    }

    //Delete user
    public static void deleteUser(EntityManager em) {
        System.out.println("Skriv in ditt ID: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
                System.out.println("Användare raderad");
            } else
                System.out.println("Ingen användare hittades med ID: " + userId);
        });
    }

    //Show all users
    public static void showUsers(EntityManager em) {
        var query = em.createQuery("SELECT u FROM User u", User.class);
        var users = query.getResultList();
        users.forEach(System.out::println);

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
        System.out.println("Ditt resultat är: " + score + " av " + questions.length);


        int finalScore = score;
        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, finalUserId);
            if (user != null) {
                user.setUserScore(finalScore);  // Set the new score
                System.out.println("Poängen uppdaterades för: " + user.getUserName());
            } else {
                System.out.println("Ingen användare hittades");
            }
        });
    }

    //

        //View statistic
        public static void viewStatistics (EntityManager em){
            System.out.println("Visar statistik... ");

            em.createQuery("SELECT u FROM User u", User.class)
                    .getResultList()
                    .forEach(user -> {
                        System.out.println("Användare: " + user.getUserName() + " | Poäng: " + user.getUserScore());
                    });
        }
    }
