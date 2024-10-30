package com.extraAllt.extraAllt.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.extraAllt.extraAllt.models.Chat;
import com.extraAllt.extraAllt.models.ChatMessage;

@Controller
public class StompController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Chat chat(ChatMessage chat) {
        System.out.println("/chat");
        return  new Chat(chat.getContent());
    }

}
