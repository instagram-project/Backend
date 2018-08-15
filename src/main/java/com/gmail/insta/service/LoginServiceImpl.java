package com.gmail.insta.service;

import com.gmail.insta.forms.LoginForm;
import com.gmail.insta.model.Token;
import com.gmail.insta.model.User;
import com.gmail.insta.repository.TokenRepository;
import com.gmail.insta.repository.UsersRepository;
import com.gmail.insta.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoginServiceImpl implements LoginService {

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    @Autowired
    public LoginServiceImpl(TokenRepository tokenRepository, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public TokenDto login(LoginForm loginForm) {
        Optional<User> userCandidate = usersRepository.findByEmail(loginForm.getEmail());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();

            if (passwordEncoder.matches(loginForm.getPassword(), user.getHashPassword())) {
                Token token = new Token();
                token.setUser(user);
                token.setValue(UUID.randomUUID().toString());

                tokenRepository.save(token);
                return TokenDto.from(token);
            }
        } throw new IllegalArgumentException("User not found");
    }
}
