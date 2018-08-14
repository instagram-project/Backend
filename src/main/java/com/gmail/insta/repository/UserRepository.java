package com.gmail.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
