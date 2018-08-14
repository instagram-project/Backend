package com.gmail.insta.service;

import java.util.Optional;

import com.gmail.insta.model.Token;

public interface TokenService {
	Optional<Token> findOneByValue(String value);

}
