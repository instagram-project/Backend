package com.gmail.insta.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.insta.model.Comment;
import com.gmail.insta.model.User;
import com.gmail.insta.service.CommentService;
import com.gmail.insta.service.TokenService;

@RestController
public class CommentController {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	CommentService commentService;
	
	@PostMapping(value="/message/{message_id}/comment")
	public ResponseEntity<String> comment(
			@RequestBody Comment comment,
			@PathVariable("message_id") Long messageId,
			@RequestParam("token") String token){
		User user = tokenService.findOneByValue(token).get().getUser();  
		comment.setDate(new Date());
		comment.setMessage(messageId);
		comment.setUser(user.getId());
		commentService.add(comment);		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
