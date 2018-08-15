package com.gmail.insta.repository;

import com.gmail.insta.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);
}
