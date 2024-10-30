package com.extraAllt.extraAllt.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.extraAllt.extraAllt.models.AiRequest;
import com.extraAllt.extraAllt.models.AiResponse;

@Service
public class AiService {
    
    @Value("${openai.api.url}")
    String apiUrl;

    private final RestTemplate restTemplate;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AiResponse sendAiResponse(String systemDescription, String userPrompt) {
        AiRequest aiRequest = new AiRequest("gpt-4", systemDescription, userPrompt, 1);
        AiResponse aiResponse = restTemplate.postForObject(apiUrl, aiRequest, AiResponse.class);
        return aiResponse;
    }
}
