package se.iths.java24.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.User;

import java.util.List;
import java.util.Scanner;

public class StatisticsManager {

    public static void statisticsMenu(EntityManager em, Scanner scanner) {
        System.out.println("~Statistikmeny~");
        boolean back = false;

        while (!back) {
            System.out.println("""
                1 - Visa topp-10 användare
                2 - Visa alla användare
                3 - Visa mängd användare
                4 - Gå tillbaka...
                """);

            String continentChoice = scanner.nextLine();
            switch (continentChoice) {
                case "1" -> viewAllUserScores(em);
                case "2" -> viewUserAmount(em);
                case "3" -> viewTopUsers(em);
                case "4" -> back = true;
                default -> System.out.println("Ogiltigt val. Försök igen.");
        }
    }
}

    // Visa de 10 användare med högst poäng
    public static void viewTopUsers(EntityManager em) {
        System.out.println("Visar de 10 användare med högst poäng...");
        var query = em.createQuery("SELECT u FROM User u ORDER BY u.userScore DESC, u.userName ASC", User.class);
        query.setMaxResults(10);
        List<User> topUsers = query.getResultList();

        if (!topUsers.isEmpty()) {
            User topUser = topUsers.get(0);
            System.out.println("🔥 Användare: " + topUser.getUserName() + " | Poäng: " + topUser.getUserScore());

            for (int i = 1; i < topUsers.size(); i++) {
                User user = topUsers.get(i);
                System.out.println("Användare: " + user.getUserName() + " | Poäng: " + user.getUserScore());
            }
        } else System.out.println("Ingen användare hittades.");
    }

    private static void viewAllUserScores(EntityManager em) {
        System.out.println("Visar statistik...");
        em.createQuery("SELECT u FROM User u", User.class)
                .getResultList()
                .forEach(user -> {
                    System.out.println("Användare: " + user.getUserName() + " | Poäng: " + user.getUserScore());
                });
    }

    // Visa hur många användare som finns
    public static void viewUserAmount(EntityManager em) {
        System.out.println("Visar antal användare...");

        var query = em.createQuery("SELECT COUNT(u) FROM User u");
        Long userCount = (Long) query.getSingleResult();

        System.out.println("Antal användare: " + userCount);
    }

}
