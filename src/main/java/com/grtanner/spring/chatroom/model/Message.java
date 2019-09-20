package com.grtanner.spring.chatroom.model;
// Source: https://o7planning.org/en/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
// Accessed: September 16, 2019

/**
 * WebSocket message model
 */
public class Message {
    // TODO: add message model.
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        JOIN , CHAT, LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // Creating toString
    @Override
    public String toString()
    {
        return "Message [message_type="
                + MessageType.JOIN
                + ", content="
                + content
                + ", sender="
                + sender + "]";
    }
}
