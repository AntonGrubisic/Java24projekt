package se.iths.java24.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "QuizSession", schema = "demo")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizSessionId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn
    private Answer answer;


    public Long getQuizSessionId() {
        return quizSessionId;
    }

    public void setQuizSessionId(Long quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
