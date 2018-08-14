package com.gmail.insta.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);
}
