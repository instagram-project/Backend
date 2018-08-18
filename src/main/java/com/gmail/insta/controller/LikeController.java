package com.gmail.insta.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.insta.model.Like;
import com.gmail.insta.model.User;
import com.gmail.insta.service.TokenService;
import com.gmail.insta.service.LikeService;



@RestController
public class LikeController {
	
	private final LikeService likeService;
	private final TokenService tokenService;
	
	@Autowired
    public LikeController(
    		LikeService likeService, 
    		TokenService tokenService) {
        this.likeService = likeService;
        this.tokenService = tokenService;		
	}


	@RequestMapping(value = "/like/{messageId}", method = RequestMethod.POST)
	public ResponseEntity<List<Long>> like(
			@RequestParam("token") String token,
            @PathVariable("messageId") Long messageId) {
		User user = tokenService.findOneByValue(token).get().getUser();
		
		Optional<Like> optionalLike = likeService.findLikeByUserAndMessage(user.getId(), messageId);
		if (optionalLike.isPresent()) {
            likeService.deleteByUserAndMessage(user.getId(), messageId);
		} else {
		    likeService.add(new Like(messageId, user.getId()));
		}
		
		return new ResponseEntity<>(likeService.findUsersIdByMessage(messageId), HttpStatus.OK);
	}
}
