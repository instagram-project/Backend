package com.gmail.insta.validators;

import com.gmail.insta.forms.PasswordForm;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public String validate(PasswordForm passwordForm) {
        String password = passwordForm.getPassword();
        String cPassword = passwordForm.getConfirmPassword();

        if (password == null || cPassword == null) {
            return "All form fields are required for submission.";
        }

        if (password.isEmpty() || cPassword.isEmpty()) {
            return "All form fields are required for submission.";
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
