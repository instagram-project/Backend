package com.gmail.insta.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.insta.model.Following;
import com.gmail.insta.model.Message;
import com.gmail.insta.model.User;
import com.gmail.insta.service.FollowingService;
import com.gmail.insta.service.MessageServiceImpl;
import com.gmail.insta.service.TokenService;


@RestController
public class FollowingController {
	
	private final FollowingService followingService;
	private final TokenService tokenService;
	private final MessageServiceImpl messageService;
	
	@Autowired
    public FollowingController(
            FollowingService followingService,
            MessageServiceImpl messageService,
            TokenService tokenService) {
        this.followingService = followingService;
        this.messageService = messageService;
        this.tokenService = tokenService;		
	}
	
	@GetMapping(value="/follow/messages")
	public ResponseEntity<List<Message>> follow(@RequestParam("token") String token,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
		User follower = tokenService.findOneByValue(token).get().getUser();
		Page<Message> result = messageService.findAllByUsers(
				followingService.findUsersByFollower(follower.getId()), page);
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("totalMessages", Long.toString(result.getTotalElements()));
        responseHeaders.set("totalPages", Integer.toString(result.getTotalPages()));
        responseHeaders.set("currentPage", Integer.toString(result.getNumber() + 1));
        return new ResponseEntity<>(result.getContent(), responseHeaders, HttpStatus.OK);				
	}
	
	
	@GetMapping(value = "/follow/users")
	public ResponseEntity<List<Long>> follow(@RequestParam("token") String token) {
		User follower = tokenService.findOneByValue(token).get().getUser();
		return new ResponseEntity<>(followingService.findUsersByFollower(follower.getId()), HttpStatus.OK);
	}
	

	@PostMapping(value = "/follow/users/{userId}")
	public ResponseEntity<List<Long>> follow(@RequestParam("token") String token,
                                             @PathVariable("userId") Long userId) {
		User follower = tokenService.findOneByValue(token).get().getUser();
			
		Optional<Following> optionalFollowing = followingService.findByFollowerAndUser(follower.getId(), userId);
		if (optionalFollowing.isPresent()) {
            followingService.delete(optionalFollowing.get());
		} else {
		    followingService.add(new Following(follower.getId(), userId));
		}		
		return new ResponseEntity<>(followingService.findUsersByFollower(follower.getId()), HttpStatus.OK);
	}

}
