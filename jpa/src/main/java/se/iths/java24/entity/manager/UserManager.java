package se.iths.java24.entity.manager;
import jakarta.persistence.EntityManager;
import java.util.Scanner;
import static se.iths.java24.JPAUtil.inTransaction;

public class UserManager {

    public static void userMenu (EntityManager em, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("~Användarmenyn~");
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    back = true;
                }
                case "2" -> showUsers(em);
                case "3" -> addUser(em, scanner);
                case "4" -> updateUser(em, scanner);
                case "5" -> deleteUser(em, scanner);
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }
    private static void printMenu() {
        System.out.println("""
        Välj nedan:
        1 - Gå tillbaka...
        2 - Visa användare
        3 - Lägg till användare
        4 - Uppdatera användare
        5 - Ta bort användare
        """);
    }

    //Read
    public static void showUsers(EntityManager em) {
        var query = em.createQuery("SELECT u FROM User u", se.iths.java24.entity.User.class);
        var users = query.getResultList();
        users.forEach(System.out::println);
    }

    //Create
    public static void addUser(EntityManager em, Scanner scanner) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        //int score = 0;
        se.iths.java24.entity.User user = new se.iths.java24.entity.User();
        user.setUserName(name);
        user.setUserScore(0);

        inTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }

    //Update
    public static void updateUser(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in ditt id: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            se.iths.java24.entity.User user = entityManager.find(se.iths.java24.entity.User.class, userId);
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

    //Delete
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
