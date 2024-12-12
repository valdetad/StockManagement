package com.example.StockManagement.controller.webSocket;

import com.example.StockManagement.data.model.webSocket.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @MessageMapping("/chat/{chatId}/register")
    @SendTo("/topic/{chatId}")
    public ChatMessage registerUserToChat(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable String chatId) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("chatId", chatId);
        return chatMessage;
    }

    @MessageMapping("/chat/{chatId}/send")
    @SendTo("/topic/{chatId}")
    public ChatMessage sendMessageToChat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
