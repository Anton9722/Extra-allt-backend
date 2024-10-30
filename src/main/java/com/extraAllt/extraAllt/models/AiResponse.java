package com.extraAllt.extraAllt.models;

import java.util.List;

public class AiResponse {
    private List<Choice> choices;

    public AiResponse() {

    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {
        private Message message;

        public Choice() {

        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
        
    }

}
