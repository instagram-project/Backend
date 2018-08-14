package com.gmail.insta.controller;

import com.gmail.insta.model.Message;
import com.gmail.insta.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class TestController {

    @Autowired
    MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/test")
    String test(Map<String, Object> model) {

        List<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "test";
    }

    @PostMapping("/savemessage")
    String saveMessage(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        Message message = new Message();
        message.setText(text);
        message.setDate(new Date());

        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepository.save(message);

        return "redirect:/test";
    }
}
