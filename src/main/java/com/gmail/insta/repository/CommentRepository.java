package com.gmail.insta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	Optional<List<Comment>> findAllByMessage(Long id, Sort sort);

}
