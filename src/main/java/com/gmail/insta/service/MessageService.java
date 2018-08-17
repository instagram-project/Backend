package com.gmail.insta.service;

import com.gmail.insta.model.Message;
import com.gmail.insta.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    private final static int PAGE_SIZE = 10;

    public Page<Message> findAll(Integer pageNumber) {

        Page<Message> messages = messageRepository.findAll(PageRequest.of
                (pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
        int totalPages = messages.getTotalPages();
        
        return messages;
    }

    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }

    public Message editMessage(Long id, String text){
        Optional<Message> optMessage = messageRepository.findById(id);
        Message message = optMessage.get();
        message.setText(text);
        messageRepository.save(message);

        return message;
    }

    public List<Message> findUserMessages(Long id){
        List<Message> messages = messageRepository.findByUserIdOrderByDateDesc(id);
        return messages;
    }

}
