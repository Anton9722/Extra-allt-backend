package com.extraAllt.extraAllt.models;

public class UpdatePointsRequest {
    private int points;
    private String userId;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

