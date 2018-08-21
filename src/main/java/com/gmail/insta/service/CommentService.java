package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;


import com.gmail.insta.model.Comment;

public interface CommentService {
	
	void add (Comment comment);
	
	Optional<List<Comment>> findAllByMessage(Long id);

}
