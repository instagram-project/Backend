package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	
	@Override
	public Optional<List<Comment>> findAllByMessage(Long id){
		return commentRepository.findAllByMessage(id, new Sort(Sort.Direction.ASC, "date"));
	}

}
