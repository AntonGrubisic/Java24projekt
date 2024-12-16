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

        //Ask user for information
        System.out.println("Enter you name: ");

        // Display menu options
        printMenu();
        while (!quit) {
            System.out.println("Choose an option:");
            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Closing program...");
                    quit = true;
                    break;
                case 1:
                    addUser(em);
                    break;
                case 2:
                    updateUser(em);
                    break;
                case 3:
                    deleteUser(em);
                    break;
                case 4:
                    showUsers(em);
                    break;
                case 5:
                    startQuiz(em);
                    break;
                case 6:
                    viewStatistics(em);
                    break;


                default:
                    System.out.println("Invalid choice.");
            }

        }
//        System.out.print("Enter search term: ");
//        Scanner scanner = new Scanner(System.in);
//        String name = scanner.nextLine();
//
//
//
//        //JPQL
//        String queryStr = "SELECT c FROM Country c WHERE c.countryName =:name";
//        TypedQuery<Country> query = em.createQuery(queryStr, Country.class);
//        query.setParameter("name", name);
//        List<Country> countries = query.getResultList();
//        countries.forEach(System.out::println);

        //Create new country
        Country country = new Country();
        country.setCountryName("Poland");
        country.setCountryCode("pl");

//        var transaction = em.getTransaction();
//        transaction.begin();
//        em.persist(country);
//        transaction.commit();
//        em.close();

        //Create
        try {
            inTransaction(entityManager -> {
                entityManager.persist(country);
            });
        } catch (Exception e) {

        }

        //Update
        inTransaction(entityManager -> {
            //QuizSession score = entityManager.find(User.class, "Kalle");
            //if (Question == correct)
            // Kalle.setQuizSessionScore()
            Country poland = entityManager.find(Country.class, "pl");
            if (poland != null) {

                poland.setCountryName("Poland (PL)");
                poland.setCountryName("Test");
            }
        });

        //Delete
        inTransaction(entityManager -> {
            Country poland = entityManager.find(Country.class, "pl");
            if (poland != null)
                entityManager.remove(poland);
        });

        inTransaction(entityManager -> {
            var country1 = entityManager.find(Country.class, "se");
            System.out.println(country1.getThreeLetterName());
        });

        //Use JOIN FETCH to prevent N + 1 problem
        inTransaction(entityManager -> {
            var c = entityManager.createQuery("SELECT c FROM Country c JOIN FETCH c.cities", Country.class)
                    .getResultList();
            c.forEach(System.out::println);
        });

        //Named entity graph to prevent N + 1 problem, defined in Entity class
        inTransaction(entityManager -> {
            var eg = entityManager.getEntityGraph("Country.cities");

            var c = entityManager.createQuery("SELECT c FROM Country c", Country.class)
                    .setHint("jakarta.persistence.fetchgraph", eg)
                    .getResultList();
            c.forEach(System.out::println);
        });

        //Create entity graph using code.
        inTransaction(entityManager -> {
            var eg = entityManager.createEntityGraph(Country.class);
            eg.addAttributeNodes("cities");

            var c = entityManager.createQuery("SELECT c FROM Country c", Country.class)
                    .setHint("jakarta.persistence.fetchgraph", eg)
                    .getResultList();
            c.forEach(System.out::println);
        });

        //Only retrieve what we need
        inTransaction(entityManager -> {
            var c = entityManager.createQuery("SELECT c.countryName FROM Country c", String.class)
                    .getResultList();
            c.forEach(System.out::println);
        });

        //Convert selected Entity to dto object
        inTransaction(entityManager -> {
            var c = entityManager.createQuery("SELECT c FROM Country c", Country.class)
                    .getResultList();
            c.stream().map(country1 ->
                            new CountryCodeAndName(country1.getCountryCode(),
                                    country1.getCountryName()))
                    .forEach(System.out::println);
        });

        //Select information into dto directly, Projection
        inTransaction(entityManager -> {
            var c = entityManager.createQuery("SELECT new se.iths.java24.CountryCodeAndName(c.countryCode, c.countryName)" +
                            " FROM Country c", CountryCodeAndName.class)
                    .getResultList();
            c.forEach(System.out::println);
        });

        //Native query, gives us access to full sql
//        inTransaction(entityManager -> {
//            var c = entityManager.createNativeQuery("delete from country where country_code='tt'")
//                    .executeUpdate();
//        });


    }

    private static void printMenu() {
        System.out.println("Pick an option: ");
        System.out.println("""
                0 - Close
                1 - Add user
                2 - Update user
                3 - Delete user
                4 - Show all users
                5 - Start new quiz
                6 - View statistics
                """);

    }

    // Adding new user to database
    public static void addUser(EntityManager em) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User();
        user.setUserName(name);

        inTransaction(entityManager -> {
            em.persist(user);
        });
    }

    //Update user
    public static void updateUser(EntityManager em) {
        System.out.println("Enter your id: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();


        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                System.out.println("Enter your new name: ");
                String newUserName = scanner.nextLine();
                user.setUserName(newUserName);
                System.out.println("Username updated successfully!");
            } else
                System.out.println("User not found.");
        });
    }


    //Delete user
    public static void deleteUser(EntityManager em) {
        System.out.println("Enter your id: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
                System.out.println("User has been deleted");
            } else
                System.out.println("User not found");
        });
    }

    //Show all users
    public static void showUsers(EntityManager em) {
        var query = em.createQuery("SELECT u FROM User u", User.class);
        var users = query.getResultList();
        users.forEach(System.out::println);
    }


    // start quiz
    public static void startQuiz(EntityManager em) {
        System.out.println("Starting quiz... ");
    }

    // View statistic
    public static void viewStatistics(EntityManager em) {
        System.out.println("Displaying statistic... ");


    }
}
