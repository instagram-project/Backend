package com.gmail.insta.service;

import com.gmail.insta.forms.PasswordForm;
import com.gmail.insta.forms.UserForm;
import com.gmail.insta.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    User signUp(UserForm userForm);

    Optional<User> findByConfirmationToken(String token);

    Optional<User> findByResetToken(String token);

    void activateUser(User user);

    User reset(User user);

    void changePassword(User user, PasswordForm passwordForm);
}
