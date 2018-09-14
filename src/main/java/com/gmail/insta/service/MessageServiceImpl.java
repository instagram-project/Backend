package com.gmail.insta.service;

import com.gmail.insta.model.Message;
import com.gmail.insta.model.User;
import com.gmail.insta.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TokenService tokenService;

    @Value("${upload.path}")
    private String uploadPath;

    private final static int PAGE_SIZE = 10;

    public List<Message> getOrderedMessages() {

        List<Message> messages = messageRepository.findAllByOrderByDateDesc();

        return messages;
    }

    public Message getMessageById(long id) {
        Message message = messageRepository.findById(id);

        return message;
    }

    public void saveNewMessage(String text, MultipartFile file, String token) throws IOException {
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

        if (token != null) {
            User user = tokenService.findOneByValue(token).get().getUser();
            message.setUserId(user.getId());
        }

        messageRepository.save(message);
    }

    public Page<Message> findAll(Integer pageNumber) {

        Page<Message> messages = messageRepository.findAll(PageRequest.of
                (pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "date"));
        int totalPages = messages.getTotalPages();

        return messages;
    }

    public Page<Message> findAllByUsers(List<Long> users, Integer pageNumber) {
        return messageRepository.findByUserIdIn(users, PageRequest.of
                (pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message editMessage(Long id, String text) {
        Optional<Message> optMessage = messageRepository.findById(id);
        Message message = optMessage.get();
        message.setText(text);
        messageRepository.save(message);

        return message;
    }

    public List<Message> findUserMessages(Long id) {
        List<Message> messages = messageRepository.findByUserIdOrderByDateDesc(id);
        return messages;
    }

}
