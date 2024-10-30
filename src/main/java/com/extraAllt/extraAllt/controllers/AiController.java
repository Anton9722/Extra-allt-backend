package com.extraAllt.extraAllt.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.extraAllt.extraAllt.models.AiResponse;
import com.extraAllt.extraAllt.services.AiService;

@RestController
@CrossOrigin(origins = "*")
public class AiController {

    @Autowired
    private AiService aiService;
    
    @PostMapping("/help-me")
    public String postChat(@RequestBody Map<String, String> requestData) {
        String systemDescription = requestData.get("systemDescription");
        String prompt = requestData.get("prompt");
    
        AiResponse response = aiService.sendAiResponse(systemDescription, prompt);
    
        return response.getChoices().get(0).getMessage().getContent();
    }
}
