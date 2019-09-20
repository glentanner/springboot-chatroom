import com.grtanner.spring.chatroom.controller.ChatController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * These tests test for page loading (default "/", "/login", "/chat", "/logout").
 *
 * Source: https://spring.io/guides/gs/testing-web/
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ChatControllerTest.class);

    @Autowired
    private ChatController mController;

    @LocalServerPort  // same as @Value("${local.server.port}")
    private int mPort;


    private String mHost = "http://localhost:";
    private String mDefaultPath = "/";
    private String mLoginPath = "/login";
    private String mLogoutPath = "/logout";
    private String mChatPath = "/chat";
    private String mLoginForm = "loginForm";

    @Autowired
    private TestRestTemplate mRestTemplate;

    /**
     * Baby steps...
     * Simple test to make sure the context is creating the ChatController.
     */
    @Test
    public void contextLoads() throws Exception {
        // assertThat uses AssertJ.  See pom.xml dependencies.
        assertThat(mController).isNotNull();
        logger.info("Started ChatControllerTest on port: " + mPort);
    }

    /**
     * Test to see that the index.html is redirecting to login.html
     * We are testing to see if the returned page contains "username"
     *
     * @throws Exception
     */
    @Test
    public void defaultPageTest() throws Exception {
        assertThat(this.mRestTemplate.getForObject(mHost + mPort + mDefaultPath,
                String.class)).contains(mLoginForm);
        logger.info("ChatControllerTest the DEFAULT page successfully redirected to LOGIN!");
    }

    /**
     * Test to see that the index.html is redirecting to login.html
     * We are testing to see if the returned page contains "username"
     *
     * @throws Exception
     */
    @Test
    public void loginPageTest() throws Exception {
        assertThat(this.mRestTemplate.getForObject(mHost + mPort + mLoginPath,
                String.class)).contains(mLoginForm);
        logger.info("ChatControllerTest the LOGIN page load was successful!");
    }

    /**
     * Test to see that the chat.html loads.
     *
     * @throws Exception
     */
    @Test
    public void chatPageTest() throws Exception {
        assertThat(this.mRestTemplate.getForObject(mHost + mPort + mChatPath,
                String.class)).contains(mChatPath);
        logger.info("ChatControllerTest the CHAT page load was successful!");
    }

    /**
     * Test logout functionality, as far as page loading goes.
     * Expect: redirect to login page.
     *
     * @throws Exception
     */
    @Test
    public void logoutpageTest() throws Exception {
        assertThat(this.mRestTemplate.getForObject(mHost + mPort + mLogoutPath,
                String.class)).contains(mLoginForm);
        logger.info("ChatControllerTest the LOGOUT page successfully redirected back to LOGIN!");
    }



}