package com.gmail.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.insta.model.Comment;
import com.gmail.insta.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Override
	public void add (Comment comment) {
		commentRepository.save(comment);
	}

}
