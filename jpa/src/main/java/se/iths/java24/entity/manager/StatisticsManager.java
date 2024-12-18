package se.iths.java24.entity.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.User;

public class StatisticsManager {

    public static void viewStatistics (EntityManager em){
        System.out.println("Visar statistik...");
        printStatistics(em);
    }

    private static void printStatistics(EntityManager em) {
        em.createQuery("SELECT u FROM User u", User.class)
                .getResultList()
                .forEach(user -> {
                    System.out.println("Användare: " + user.getUserName() + " | Poäng: " + user.getUserScore());
                });
    }
}
