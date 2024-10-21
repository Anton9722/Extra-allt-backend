package com.extraAllt.extraAllt.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean premium;
    private int points;
    private List<String> solvedProblems;

    public User(String id, String username, String password, boolean premium, int points, List<String> solvedProblems) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.premium = false;
        this.points = points;
        this.solvedProblems = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<String> getSolvedProblems() {
        return solvedProblems;
    }

    public void setSolvedProblems(List<String> solvedProblems) {
        this.solvedProblems = solvedProblems;
    }

}
