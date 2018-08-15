package com.gmail.insta.controller;

import com.gmail.insta.model.Message;
import com.gmail.insta.repository.MessageRepository;
import com.gmail.insta.service.MessageService;
import com.gmail.insta.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class MainController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @Autowired
    UserServiceImpl userService;

    @Value("${upload.path}")
    private String uploadPath;

    // Получить список всех сообщений
    @GetMapping(value = "/all", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<Message>> getMessages() {

        List<Message> messages = messageRepository.findAll();

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
        Message message = messageRepository.findById(id);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Загрузка сообщения на сервер
    @PostMapping(value = "/upload", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<Message> uploadMessage(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "userId", required = false) Long userId
    ) throws IOException {
        Message message = new Message();
        message.setText(text);
        message.setDate(new Date());

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        message.setAuthor(userService.findById(userId).get());

        messageRepository.save(message);

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
