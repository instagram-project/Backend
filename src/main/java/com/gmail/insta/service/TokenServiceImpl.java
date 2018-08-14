package com.gmail.insta.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.insta.model.Token;
import com.gmail.insta.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService{

	@Autowired
	TokenRepository tokenRepository;
	
	@Override
	public Optional<Token> findOneByValue(String value) {
		return tokenRepository.findOneByValue(value);
	}
	
}
