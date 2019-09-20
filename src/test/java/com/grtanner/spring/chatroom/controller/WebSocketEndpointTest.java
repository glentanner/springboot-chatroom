import com.fasterxml.jackson.databind.ObjectMapper;
import com.grtanner.spring.chatroom.model.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source: https://github.com/rafaelhz/testing-spring-websocket/blob/master/src/test/java/WebSocketTest.java
 * Author: Rafael Zeffa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketEndpointTest {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEndpointTest.class);

    static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    static final String WEBSOCKET_TOPIC = "/topic/publicChatRoom";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                    asList(new WebSocketTransport(new StandardWebSocketClient()))));

        // We need a TaskScheduler so that we can setAutoReceipt() on the StompSession.
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler);
        stompClient.setReceiptTimeLimit(5000);

        logger.info("WebSocketEndpointTest setup() completed.");
    }

    @Test
    public void testJOIN() throws Exception {
        // Creating object of Message
        Message tempMessage = new Message();

        // Insert the data into the object
        tempMessage.setType(Message.MessageType.JOIN);
        tempMessage.setSender("Jenny");

        // Creating Object of ObjectMapper define in Jackson Api
        ObjectMapper Obj = new ObjectMapper();

        // get Message object as a json string
        String jsonStr = Obj.writeValueAsString(tempMessage);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        session.setAutoReceipt(true);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        session.send(WEBSOCKET_TOPIC, jsonStr.getBytes());

        Assert.assertEquals(jsonStr, blockingQueue.poll(1, SECONDS));
        logger.info("WebSocketEndpointTest: Message= " +jsonStr);
    }

    @Test
    public void testCHAT() throws Exception {
        // Creating object of Message
        Message tempMessage = new Message();

        // Insert the data into the object
        tempMessage.setType(Message.MessageType.CHAT);
        tempMessage.setSender("Jenny");

        // Creating Object of ObjectMapper define in Jackson Api
        ObjectMapper Obj = new ObjectMapper();

        // get Message object as a json string
        String jsonStr = Obj.writeValueAsString(tempMessage);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        session.setAutoReceipt(true);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        session.send(WEBSOCKET_TOPIC, jsonStr.getBytes());

        Assert.assertEquals(jsonStr, blockingQueue.poll(1, SECONDS));
        logger.info("WebSocketEndpointTest: Message= " +jsonStr);
    }

    @Test
    public void testLEAVE() throws Exception {
        // Creating object of Message
        Message tempMessage = new Message();

        // Insert the data into the object
        tempMessage.setType(Message.MessageType.LEAVE);
        tempMessage.setSender("Jenny");

        // Creating Object of ObjectMapper define in Jackson Api
        ObjectMapper Obj = new ObjectMapper();

        // get Message object as a json string
        String jsonStr = Obj.writeValueAsString(tempMessage);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        session.setAutoReceipt(true);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        session.send(WEBSOCKET_TOPIC, jsonStr.getBytes());

        Assert.assertEquals(jsonStr, blockingQueue.poll(1, SECONDS));
        logger.info("WebSocketEndpointTest: Message= " +jsonStr);
    }

    class DefaultStompFrameHandler extends StompSessionHandlerAdapter {//implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object payload) {
            blockingQueue.offer(new String((byte[]) payload));
        }
    }

}