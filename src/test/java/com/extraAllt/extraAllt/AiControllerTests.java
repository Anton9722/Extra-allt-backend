package com.extraAllt.extraAllt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.extraAllt.extraAllt.services.AiService;
import com.extraAllt.extraAllt.models.AiResponse;
import com.extraAllt.extraAllt.models.AiResponse.Choice;
import com.extraAllt.extraAllt.models.Message;
import com.extraAllt.extraAllt.controllers.AiController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AiControllerTests {

    @Mock
    private AiService aiService;

    @InjectMocks
    private AiController aiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostChat() {
        AiResponse.Choice choice = new AiResponse.Choice();
        choice.setMessage(new Message(null, "mockad ai response"));
        
        AiResponse aiResponse = new AiResponse();
        aiResponse.setChoices(Collections.singletonList(choice));

        when(aiService.sendAiResponse(any(), any())).thenReturn(aiResponse);

        Map<String, String> requestData = new HashMap<>();
        requestData.put("systemDescription", "Mock system description");
        requestData.put("prompt", "Test prompt");

        String response = aiController.postChat(requestData);

        assertEquals("mockad ai response", response);
    }
}


