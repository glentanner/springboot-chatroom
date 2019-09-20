package com.grtanner.spring.chatroom.config;
// Source: https://o7planning.org/en/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
// Accessed: September 16, 2019

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     *  This creates the MessageBroker and defines the endpoint as "/ws".
     *  The MessageBroker exposes an  endpoint  so that the  client can contact and form a connection using SockJS.
     *
     *  The JavaScript code (on the client side) to connect would then look something like this:
     *      var socket = new SockJS('/ws');
     *      stompClient = Stomp.over(socket);
     *
     * Also, we include registration for an endpoint without SockJs, in case it is wonky.
     *      stompClient.connect({}, onConnected, onError);
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * The MessageBroker exposes two kinds of destinations.
     *
     * '/topic' means the topic that the client can subscribe to, when a topic has messages.
     * The messages will then be sent to the clients that subscribed to this topic.
     *
     * '/app' means the places where the client can give messages to the WebSocket server.
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic");
    }
}
