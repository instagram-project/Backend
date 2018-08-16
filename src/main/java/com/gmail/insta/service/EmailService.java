package com.gmail.insta.service;

import com.gmail.insta.model.User;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendRegistrationEmail(User user);

    @Async
    void sendResetEmail(User user);
}
