package com.gmail.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.insta.model.User;
import com.gmail.insta.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User update (User user) {
		return userRepository.save(user);
	}
	
}
