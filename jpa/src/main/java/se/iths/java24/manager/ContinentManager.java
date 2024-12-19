package se.iths.java24.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Answer;
import se.iths.java24.entity.Question;
import se.iths.java24.entity.User;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.inTransaction;

public class ContinentManager {

    public static void continentMenu(EntityManager em, Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("Välj ett alternativ: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> startQuiz(em, scanner);
                case "2" -> addQuestion(em, scanner);
                case "3" -> addAnswer(em, scanner);
                case "4" -> updateQuestion(em, scanner);
                case "5" -> deleteQuestion(em, scanner);
                case "6" -> showQuestions(em);
                case "7" -> {
                    System.out.println("Återgår till huvudmenyn...");
                    exit = true;
                }
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                ~Quiz-meny~
                1 - Starta nytt quiz
                2 - Lägg till fråga
                3 - Lägg till Svar
                4 - Uppdatera fråga
                5 - Ta bort fråga
                6 - Visa frågor
                7 - Gå tillbaka...
                """);
    }

    private static void startQuiz(EntityManager em, Scanner scanner) {
        int score = 0;  // Initialize the score

        System.out.println("Välkommen till Kontinentquizet!");
        System.out.println("Ange ditt användarID: ");
        Long userId = scanner.nextLong();

        var questions = em.createQuery("select q from Question q", Question.class).getResultList();

        if (questions.isEmpty()) {
            System.out.println("Inga frågor hittades för quizet.");
            return;
        }

        for (Question question : questions) {
            System.out.println("Fråga: " + question.getQuestionText());

            var answers = em.createQuery("select a from Answer a WHERE a.question = :question", Answer.class)
                    .setParameter("question", question)
                    .getResultList();

            for (int i = 0; i < answers.size(); i++) {
                System.out.println((i + 1) + ". " + answers.get(i).getOptionText());
            }

            System.out.println("Ditt svar: ");
            int userAnswer = scanner.nextInt();
            scanner.nextLine();

            if (userAnswer > 0 && userAnswer <= answers.size() && answers.get(userAnswer - 1).isCorrect()) {
                score++;
            }
        }

        System.out.println("Ditt resultat: " + score + " av " + questions.size());

        int finalScore = score;
        inTransaction(entityManager -> {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                user.setUserScore(finalScore);
                System.out.println("Dina poäng har uppdaterats");
            } else {
                System.out.println("Ingen användare hittades med ID: " + userId);
            }
        });
    }

    public static void addQuestion(EntityManager em, Scanner scanner) {
        System.out.print("Skriv in en ny fråga: ");
        String newQuestion = scanner.nextLine();
        Question question = new Question();
        question.setQuestionText(newQuestion);
        inTransaction(entityManager -> entityManager.persist(question));
        System.out.println("Frågan har lagts till.");
    }


    public static void addAnswer(EntityManager em, Scanner scanner) {
        System.out.print("Skriv in fråge-ID som du vill lägga till ett svar till: ");
        Long questionId = scanner.nextLong();
        scanner.nextLine();

        Question question = em.find(Question.class, questionId);
        if (question == null) {
            System.out.println("Ingen fråga hittades med ID: " + questionId);
            return;
        }
        System.out.print("Skriv in svarsalternativ: ");
        String newAnswer = scanner.nextLine();

        System.out.print("Är detta det korrekta svaret? (true/false): ");
        boolean isCorrect = scanner.nextBoolean();
        scanner.nextLine();
        Answer answer = new Answer();
        answer.setQuestion(question);
        //answer.setOptionText(newAnswer);
        answer.setCorrect(isCorrect);

        inTransaction(entityManager -> entityManager.persist(answer));
        System.out.println("Svaret har lagts till.");
    }


    public static void updateQuestion(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in questionId: ");
        Long questionId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            Question question = entityManager.find(Question.class, questionId);
            if (question != null) {
                System.out.println("Skriv in den nya frågan: ");
                String newlyMadeQuestion = scanner.nextLine();
                question.setQuestionText(newlyMadeQuestion);


                if (!entityManager.contains(question)) {
                    question = entityManager.merge(question);
                }
                ;

                System.out.println("Frågan har uppdaterats.");
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
        var questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        if (questions.isEmpty()) {
            System.out.println("Inga frågor hittades.");
            return;
        }

        questions.forEach(question -> {
            System.out.println("Fråga: " + question.getQuestionText());
            var answers = em.createQuery("SELECT a FROM Answer a WHERE a.question = :question", Answer.class)
                    .setParameter("question", question)
                    .getResultList();

            answers.forEach(answer -> System.out.println("- " + answer.getOptionText() + " (Rätt: " + answer.isCorrect() + ")"));
        });
    }
}
