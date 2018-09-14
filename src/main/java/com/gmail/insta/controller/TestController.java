package com.gmail.insta.controller;

import com.gmail.insta.model.Message;
import com.gmail.insta.model.User;
import com.gmail.insta.repository.MessageRepository;
import com.gmail.insta.repository.UsersRepository;
import com.gmail.insta.service.MessageServiceImpl;
import com.gmail.insta.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.*;

@Controller
public class TestController {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ServletContext servletContext;

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    UserServiceImpl userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/test")
    String test(Map<String, Object> model) {

        List<Message> messages = messageService.getOrderedMessages();

        model.put("messages", messages);

        Collection<User> users = userService.findAllUsers();

        model.put("users", users);

        return "test";
    }
}
