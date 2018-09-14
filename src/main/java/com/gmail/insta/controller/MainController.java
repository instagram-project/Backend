package com.gmail.insta.controller;

import com.gmail.insta.model.Message;
import com.gmail.insta.model.User;
import com.gmail.insta.service.MessageServiceImpl;
import com.gmail.insta.service.TokenService;
import com.gmail.insta.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class MainController {

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenService tokenService;

    // Получить список всех сообщений
    @GetMapping(value = "/all", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<Message>> getMessages() {

        List<Message> messages = messageService.getOrderedMessages();

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Получить список сообщений с пагинацией
    @GetMapping(value = "/", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<Collection<Message>> getMessages(
            @RequestParam(value = "page", required = false) Integer pageId) {
        Page<Message> list = messageService.findAll(pageId == null ? 1 : pageId);
        Collection<Message> messages = list.getContent();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("totalMessages", Long.toString(list.getTotalElements()));
        responseHeaders.set("totalPages", Integer.toString(list.getTotalPages()));
        responseHeaders.set("currentPage", Integer.toString(list.getNumber() + 1));

        return new ResponseEntity<>(messages, responseHeaders, HttpStatus.OK);
    }

    // Получить конкретное сообщение
    @GetMapping(value = "/message", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<Message> findMessage(
            @RequestParam(value = "messageId", required = false) Long messageId) {
        long id = messageId;
        Message message = messageService.getMessageById(id);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Получить список сообщений конкретного юзера
    @GetMapping(value = "/feed/user/{userId}", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<Message>> getUserMessages(
            @PathVariable("userId") Long userId
    ) {

        List<Message> messages = messageService.findUserMessages(userId);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Получить список сообщений юзера по токену
    @GetMapping(value = "/feed/user_messages", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<Message>> getUserMessagesByToken(
            @RequestParam("token") String token
    ) {
        User user = tokenService.findOneByValue(token).get().getUser();
        List<Message> messages = messageService.findUserMessages(user.getId());

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Загрузка сообщения на сервер
    @PostMapping(value = "/upload", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<Message> uploadMessage(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "token", required = false) String token
            //@RequestParam(value = "userId", required = false) Long userId
    ) {

        try {
            messageService.saveNewMessage(text, file, token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<Message> delMessage(
            @RequestParam("messageId") Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/edit")
    public ResponseEntity<Message> updateMessage(
            @RequestParam("messageId") Long messageId,
            @RequestParam("text") String text)
    {
        Message message = messageService.editMessage(messageId, text);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
