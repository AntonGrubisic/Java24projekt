package se.iths.java24.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "User", schema = "demo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column
    private Integer userScore;

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserScore(){
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    @Override
    public String toString() {
        return "AnvändarID: " + userId + ", Namn: " + userName + ", Poäng: " + userScore + ".";
    }
}
