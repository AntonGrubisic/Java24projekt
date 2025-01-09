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

    @Column (nullable = false)
    private String userCountry;

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCountry(){
        return userCountry;
    }

    public void setUserCountry(String userCountry){
        this.userCountry = userCountry;
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + userName + ", Country: " + userCountry;
    }
}
