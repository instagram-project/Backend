package com.gmail.insta.service;

import com.gmail.insta.model.Message;
import com.gmail.insta.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
