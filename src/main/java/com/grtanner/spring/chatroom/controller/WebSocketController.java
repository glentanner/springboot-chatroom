package com.grtanner.spring.chatroom.controller;
// Source: https://o7planning.org/en/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
// Accessed: September 16, 2019

import com.grtanner.spring.chatroom.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Takes the place of WebSocketServer.
 */
@Controller
public class WebSocketController {

    //TODO: add send message.
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public Message sendMessage(@Payload Message message) throws Exception {
        return message;
    }

    //TODO: 1) get session, 2) add user.
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        // Add username to the websocket session.
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
