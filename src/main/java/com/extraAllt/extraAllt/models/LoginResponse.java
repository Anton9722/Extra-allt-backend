package com.extraAllt.extraAllt.models;

public class LoginResponse {
    private boolean success;
    private String message;
    private String userId;

    public LoginResponse(boolean success, String message, String userId) {
        this.success = success;
        this.message = message;
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
