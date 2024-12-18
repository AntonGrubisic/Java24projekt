package se.iths.java24.entity.manager;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import se.iths.java24.JPAUtil;
import se.iths.java24.entity.Answer;
import se.iths.java24.entity.Question;
import se.iths.java24.entity.User;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.inTransaction;

public class QuizManager {

    public static void QuizMenu(EntityManager em, Scanner scanner) {
        printMenu();
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                break;
            case "2":
                startQuiz(em, scanner);
                break;
            case "3":
                addQuestion(em, scanner);
                break;
            case "4":
                addAnswer(em, scanner);
                break;
            case "5":
                updateQuestion(em, scanner);
                break;
            case "6":
                deleteQuestion(em, scanner);
                break;
            case "7":
                showQuestions(em);
                break;
        }
    }

    private static void printMenu () {
        System.out.println("""
        ~Quiz-menyn~
        1 - Gå tillbaka...
        2 - Starta nytt quiz
        3 - Lägg till fråga
        4 - Lägg till Svar
        5 - Uppdatera fråga
        6 - Ta bort fråga
        7 - Visa frågor
        """);
    }

    private static void startQuiz(EntityManager em, Scanner scanner) {
        int score = 0;  // Initialize the score
        long userId = 0;
        System.out.println("Välkommen till quizet!");
        System.out.println("Ange ditt userId: ");
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
                System.out.println("Ingen användare hittades.");
            }
        });
    }

    public static void addQuestion(EntityManager em, Scanner scanner) {
        System.out.println("Skapa en ny fråga: ");
        String newQuestion = scanner.nextLine();
        Question question = new Question();
        question.setQuestionText(newQuestion);

        inTransaction(entityManager -> {
            entityManager.persist(question);
        });
    }

    public static void addAnswer(EntityManager em, Scanner scanner) {
        System.out.println("Skapa ett nytt svar: ");
        String newAnswer = scanner.nextLine();
        Answer answer = new Answer();
        answer.setOptionText(newAnswer);

        inTransaction(entityManager -> {
            entityManager.persist(answer);
        });
    }

    public static void updateQuestion(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in ditt questionId: ");
        Long questionId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            Question question = entityManager.find(Question.class, questionId);
            if (question != null) {
                System.out.println("Skriv in den nya frågan: ");
                String newlyMadeQuestion = scanner.nextLine();
                question.setQuestionText(newlyMadeQuestion);

                // Om entiteten är detached kopplas den tillbaka
                if (!entityManager.contains(question)) {
                    question = entityManager.merge(question);
                }
                ;

                System.out.println("Frågan har uppdaterats");
            } else
                System.out.println("Ingen fråga hittades med ID: " + questionId);
        });
    }

    public static void deleteQuestion(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in frågeId: ");
        Long questionId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            Question question = entityManager.find(Question.class, questionId);
            if (question != null) {
                entityManager.remove(question);
                System.out.println("Fråga raderad.");
            } else
                System.out.println("Ingen fråga hittades med ID: " + questionId);
        });
    }

    public static void showQuestions(EntityManager em) {
        var query = em.createQuery("SELECT q FROM Question q", Question.class);
        var questions = query.getResultList();
        questions.forEach(System.out::println);

    }
}
