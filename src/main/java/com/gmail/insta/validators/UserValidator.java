package com.gmail.insta.validators;

import com.gmail.insta.forms.UserForm;
import com.gmail.insta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public String validate(UserForm userForm) {
        String name = userForm.getName();
        String email = userForm.getEmail();
        String password = userForm.getPassword();
        String cPassword = userForm.getConfirmPassword();

        if (name == null || email == null || password == null || cPassword == null) {
            return "All form fields are required for submission.";
        }

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
            return "All form fields are required for submission.";
        }

        if (userService.findByEmail(email).isPresent()) {
            return "Someone already has that email.";
        }

        if (password.length() < 8 || password.length() > 16) {
            return "Please use between 8 and 16 characters for password.";
        }

        if (!password.equals(cPassword)) {
            return "These passwords don't match.";
        }

        return null;
    }
}
