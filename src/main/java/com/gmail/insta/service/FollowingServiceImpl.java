package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.insta.model.Following;
import com.gmail.insta.model.Like;
import com.gmail.insta.repository.FollowingRepository;

@Service
public class FollowingServiceImpl implements FollowingService {
	
	@Autowired
	FollowingRepository followingRepository;
	
	@Override
	public void add(Following f) {
		followingRepository.save(f);
	}
	
	@Override
	public void delete(Following f) {
		followingRepository.delete(f);
	}
	
	@Override
	public Optional<Following> findByFollowerAndUser(Long follower, Long user){
		return followingRepository.findByFollowerAndUser(follower, user);
	}
	
	@Override
	public List<Long> findUsersByFollower(Long id){
		List<Following> f = followingRepository.findByFollower(id);
		return f.stream().map(Following::getUser).collect(Collectors.toList());
	}

}
