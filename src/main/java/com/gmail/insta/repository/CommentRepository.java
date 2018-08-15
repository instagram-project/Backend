package com.gmail.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
