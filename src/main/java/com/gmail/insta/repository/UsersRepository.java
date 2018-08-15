package com.gmail.insta.repository;

import com.gmail.insta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String token);

    Optional<User> findByResetToken(String token);

    Optional<User> findById(Long id);
}
