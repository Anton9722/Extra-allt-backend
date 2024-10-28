package com.extraAllt.extraAllt.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
    
    @MessageMapping("/update")
    @SendTo("/topic/update-leaderboard")
    public String update() {
        System.out.println("Jag kör");
        return("test");
    }

}
