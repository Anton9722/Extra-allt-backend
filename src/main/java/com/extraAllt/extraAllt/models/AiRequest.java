package com.extraAllt.extraAllt.models;

import java.util.ArrayList;
import java.util.List;

public class AiRequest {
    private String model;
    private List<Message> messages;
    private int n;
    
    public AiRequest(String model, String systemDescription, String userPrompt, int n) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", systemDescription));
        this.messages.add(new Message("user", userPrompt));
        this.n = n;
    }
    

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    
}
