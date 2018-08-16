package com.gmail.insta.controller;

import com.gmail.insta.forms.LoginForm;
import com.gmail.insta.forms.PasswordForm;
import com.gmail.insta.forms.UserForm;
import com.gmail.insta.model.User;
import com.gmail.insta.service.EmailService;
import com.gmail.insta.service.LoginService;
import com.gmail.insta.service.UserService;
import com.gmail.insta.transfer.TokenDto;
import com.gmail.insta.validators.PasswordValidator;
import com.gmail.insta.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final LoginService loginService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final PasswordValidator passwordValidator;
    private final EmailService emailService;

    @Autowired
    public UserController(LoginService loginService, UserService userService, UserValidator userValidator, PasswordValidator passwordValidator, EmailService emailService) {
        this.loginService = loginService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.passwordValidator = passwordValidator;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(loginService.login(loginForm));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserForm userForm) {
        String answer = userValidator.validate(userForm);

        if (answer != null) {
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        } else {
            emailService.sendRegistrationEmail(userService.signUp(userForm));
            return ResponseEntity.ok("A confirmation e-mail has been sent to " + userForm.getEmail());
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(Model model, @RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("Bad token.", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userService.findByConfirmationToken(token);
        if (optionalUser.isPresent()) {
            userService.activateUser(optionalUser.get());
            return ResponseEntity.ok(optionalUser.get().getEmail() + " activated.");
        } else {
            return new ResponseEntity<>("Bad token.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> reset(@RequestParam("email") String email) {
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            emailService.sendResetEmail(userService.reset(optionalUser.get()));
            return ResponseEntity.ok("A restore e-mail has been sent to " + optionalUser.get().getEmail());
        } else {
            return new ResponseEntity<>("Bad email.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> register(@RequestBody PasswordForm passwordForm, @RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("Bad token.", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userService.findByResetToken(token);
        if (optionalUser.isPresent()) {
            String answer = passwordValidator.validate(passwordForm);
            if (answer != null) {
                return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
            } else {
                userService.changePassword(optionalUser.get(), passwordForm);
                return ResponseEntity.ok(optionalUser.get().getEmail() + " password changed.");
            }
        } else {
            return new ResponseEntity<>("Bad token.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
