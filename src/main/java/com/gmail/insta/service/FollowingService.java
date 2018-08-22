package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;

import com.gmail.insta.model.Following;

public interface FollowingService {
	
	void add (Following f);
	void delete(Following f);
	Optional<Following> findByFollowerAndUser(Long follower, Long user);
	List<Long> findUsersByFollower(Long id);

}
