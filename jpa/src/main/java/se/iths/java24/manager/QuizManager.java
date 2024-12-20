package se.iths.java24.manager;

import jakarta.persistence.EntityManager;
import se.iths.java24.entity.Answer;
import se.iths.java24.entity.Question;

import java.util.List;
import java.util.Scanner;

import static se.iths.java24.JPAUtil.inTransaction;

public class QuizManager {

    public static void EuropaQuizMenu(EntityManager em, Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("Välj ett alternativ: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> startQuiz(em, scanner);
                case "2" -> addQuestionWithAnswers(em, scanner);
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
                4 - Uppdatera fråga
                5 - Ta bort fråga
                6 - Visa frågor
                7 - Gå tillbaka...
                """);
    }

    private static void startQuiz(EntityManager em, Scanner scanner) {
        int score = 0;

        System.out.println("Välkommen till Europaquizet!");
        System.out.println("Ange ditt användarID: ");
        Long userId = scanner.nextLong();
        scanner.nextLine();

        List<Question> questions = em.createQuery("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answers", Question.class).getResultList();

        if (questions.isEmpty()) {
            System.out.println("Inga frågor hittades för quizet.");
            return;
        }

        for (Question question : questions) {
            System.out.println("Fråga: " + question.getQuestionText());

            List<Answer> answers = question.getAnswers();

            for (int i = 0; i < answers.size(); i++) {
                System.out.println((i + 1) + ". " + answers.get(i).getOptionText());
            }

            System.out.print("Ditt svar (1-" + answers.size() + "): ");
            int userAnswer = scanner.nextInt();
            scanner.nextLine();

            if (userAnswer > 0 && userAnswer <= answers.size() && answers.get(userAnswer - 1).isCorrect()) {
                score++;
                System.out.println("Rätt svar!");
            } else {
                System.out.println("Fel svar.");
            }
        }

        System.out.println("Ditt resultat: " + score + " av " + questions.size());
    }

    private static void addQuestionWithAnswers(EntityManager em, Scanner scanner) {
        Question question = addQuestion(em, scanner);
        addAnswers(em, scanner, question);
    }

    public static Question addQuestion(EntityManager em, Scanner scanner) {
        System.out.print("Skriv in en ny fråga: ");
        String newQuestion = scanner.nextLine();
        Question question = new Question();
        question.setQuestionText(newQuestion);
        inTransaction(entityManager -> entityManager.persist(question));
        System.out.println("Frågan har lagts till.");
        return question;
    }

    public static void addAnswers(EntityManager em, Scanner scanner, Question question) {
        System.out.println("Lägger till svarsalternativ för frågan: " + question.getQuestionText());

        for (int i = 1; i <= 4; i++) {
            System.out.print("Skriv in svarsalternativ " + i + ": ");
            String optionText = scanner.nextLine();

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setOptionText(optionText);
            answer.setCorrect(false);

            inTransaction(entityManager -> entityManager.persist(answer));
        }

        System.out.print("Ange vilket svar som är korrekt (1-4): ");
        int correctOption = scanner.nextInt();
        scanner.nextLine();

        inTransaction(entityManager -> {
            List<Answer> answers = entityManager.createQuery(
                            "SELECT a FROM Answer a WHERE a.question = :question", Answer.class)
                    .setParameter("question", question)
                    .getResultList();

            if (correctOption > 0 && correctOption <= answers.size()) {
                Answer correctAnswer = answers.get(correctOption - 1);
                correctAnswer.setCorrect(true);
                entityManager.merge(correctAnswer);
                System.out.println("Svarsalternativ " + correctOption + " markerat som korrekt.");
            } else {
                System.out.println("Ogiltigt val för korrekt svar.");
            }
        });
    }

    public static void updateQuestion(EntityManager em, Scanner scanner) {
        System.out.println("Skriv in questionId: ");
        Long questionId = scanner.nextLong();
        scanner.nextLine();

        inTransaction(entityManager -> {
            Question question = entityManager.find(Question.class, questionId);
            if (question != null) {
                System.out.println("Skriv in den nya frågan: ");
                String newQuestionText = scanner.nextLine();
                question.setQuestionText(newQuestionText);
                entityManager.merge(question);
                System.out.println("Frågan har uppdaterats.");
            } else {
                System.out.println("Ingen fråga hittades med ID: " + questionId);
            }
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
            } else {
                System.out.println("Ingen fråga hittades med ID: " + questionId);
            }
        });
    }

    public static void showQuestions(EntityManager em) {
        List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();

        if (questions.isEmpty()) {
            System.out.println("Inga frågor hittades.");
            return;
        }

        questions.forEach(question -> {
            System.out.println("Fråga: " + question.getQuestionText());

            List<Answer> answers = em.createQuery("SELECT a FROM Answer a WHERE a.question = :question", Answer.class)
                    .setParameter("question", question)
                    .getResultList();

            answers.forEach(answer -> System.out.println("- " + answer.getOptionText() + " (Rätt: " + answer.isCorrect() + ")"));
        });
    }
}
