package com.grtanner.spring.chatroom.controller;
// Source: https://o7planning.org/en/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
// Accessed: September 16, 2019

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ChatController {

    /**
     * We have to have a username for the chat session.  If there isn't one, we need to redirect to the login page so that
     * the user is prompted to enter a username.
     *
     * If there is a username, just go to the chat.
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(path = "/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null || username.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);

        return "chat";
    }

    /**
     * Carries the request parameter in the URL string (username).
     * We don't want that, mostly in case we decide to ask for a password in a later version.
     * Let's redirect to the POST as a more secure way of hiding the username in the URL.
     *
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }


    /**
     * Carries the request parameter in the message body (username).
     * This is more secure.
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
         username.trim();

         if (username.isEmpty()) {
             return "login";
         }
         request.getSession().setAttribute("username", username);

         return "redirect:/";
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
         request.getSession(true).invalidate();
         return "redirect:/login";
    }
}
