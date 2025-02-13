package se.iths.java24.manager;
import jakarta.persistence.EntityManager;
import java.util.Scanner;
import static se.iths.java24.JPAUtil.inTransaction;

public class UserManager {

    public static void userMenu (EntityManager em, Scanner scanner) {
        boolean back = false;

        while (!back) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showUsers(em);
                case "2" -> addUser(em, scanner);
                case "3" -> updateUser(em, scanner);
                case "4" -> deleteUser(em, scanner);
                case "5" -> {
                    back = true;
                }
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }
    private static void printMenu() {
        System.out.println("""
        ~Användarmeny~
        1 - Visa användare
        2 - Lägg till användare
        3 - Uppdatera användare
        4 - Ta bort användare
        5 - Gå tillbaka...
        """);
    }


    public static void showUsers(EntityManager em) {
        var query = em.createQuery("SELECT u FROM User u", se.iths.java24.entity.User.class);
        var users = query.getResultList();
        users.forEach(System.out::println);
    }


    public static void addUser(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in ditt namn: ");
        String name = scanner.nextLine();
        System.out.println("Skriv in ditt land: ");
        String country = scanner.nextLine();
        se.iths.java24.entity.User user = new se.iths.java24.entity.User();
        user.setUserName(name);
        user.setUserCountry(country);

        inTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }


    public static void updateUser(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in ditt id: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            se.iths.java24.entity.User user = entityManager.find(se.iths.java24.entity.User.class, userId);
            if (user != null) {
                System.out.println("Skriv in ditt namn: ");
                String newUserName = scanner.nextLine();

                em.getTransaction().begin();
                user.setUserName(newUserName);
                em.getTransaction().commit();

                System.out.println("Användaren har uppdaterats");
            } else
                System.out.println("Ingen användare hittades med ID: " + userId);
        });
    }


    public static void deleteUser(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in ditt ID: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            se.iths.java24.entity.User user = entityManager.find(se.iths.java24.entity.User.class, userId);
            if (user != null) {
                entityManager.remove(user);
                System.out.println("Användare raderad");
            } else
                System.out.println("Ingen användare hittades med ID: " + userId);
        });
    }
}
