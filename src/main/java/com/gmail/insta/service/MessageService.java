package com.gmail.insta.service;

import com.gmail.insta.model.Message;
import com.gmail.insta.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    private final static int PAGE_SIZE = 10;

    public Page<Message> findAll(Integer pageNumber) {
        return messageRepository.findAll(PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

}
