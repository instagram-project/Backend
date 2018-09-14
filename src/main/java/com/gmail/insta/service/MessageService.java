package com.gmail.insta.service;

import com.gmail.insta.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    public List<Message> getOrderedMessages();

    public Message getMessageById(long id);

    public void saveNewMessage(String text, MultipartFile file, String token) throws IOException;

    public Page<Message> findAll(Integer pageNumber);

    public Page<Message> findAllByUsers(List<Long> users, Integer pageNumber);

    public void deleteMessage(Long id);

    public Message editMessage(Long id, String text);

    public List<Message> findUserMessages(Long id);
}
