package com.gmail.insta.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.insta.model.User;
import com.gmail.insta.service.TokenService;
import com.gmail.insta.service.UserService;
import com.gmail.insta.transfer.UserProfile;


@RestController
public class ProfileController {
		
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
		
	
	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public ResponseEntity<UserProfile> profile(
    		@RequestParam("userId") Optional<Long> userId,
    		@RequestParam("token") Optional<String> token) {        
		User user;
		if(userId.isPresent()) {
			user = userService.findById(userId.get()).get();
		} else {
		    user = tokenService.findOneByValue(token.get()).get().getUser();
		}
        return ResponseEntity.ok(UserProfile.from(user));    
    }
	
	@RequestMapping(value = "/user/profile", method = RequestMethod.PUT)
    public ResponseEntity<UserProfile> profile(
    		@RequestBody UserProfile userProfile,
    		@RequestParam("token") String token) {        
        User user = tokenService.findOneByValue(token).get().getUser();  
        user.setName(userProfile.getName());
        user = userService.update(user);
        return ResponseEntity.ok(UserProfile.from(user));    
    }
	
	@RequestMapping(value = "/user/avatar", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> avatar(
			@RequestParam(value = "userId") Optional<Long> userId,
			@RequestParam(value = "token") Optional<String> token){
		User user;
		if(userId.isPresent()) {
			user = userService.findById(userId.get()).get();
		} else {
		    user = tokenService.findOneByValue(token.get()).get().getUser();
		}
		String userPath = uploadPath + "/avatars/" + user.getId();
	    
		if(Files.notExists(Paths.get(userPath))) {
			Resource resource = new ClassPathResource("avatars/default.jpg");
		    return new ResponseEntity<>(resource, HttpStatus.OK);
		}		
		
		Resource resource = new FileSystemResource(new File(userPath + "/avatar"));
	    return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/avatar", method = RequestMethod.PUT, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> avatar(
    		@RequestParam("file") MultipartFile uploaded,
            @RequestParam("token") String token) {        
        try {
        	User user = tokenService.findOneByValue(token).get().getUser();
        	String userPath = uploadPath + "/avatars/" + user.getId();
        	Files.createDirectories(Paths.get(userPath));        	
        	
        	uploaded.transferTo(new File(userPath + "/avatar"));
        	
        	Resource resource = new FileSystemResource(
     	    		new File(userPath + "/avatar"));
     	    return new ResponseEntity<>(resource, HttpStatus.OK);            
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }    
    }


}
