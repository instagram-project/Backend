package com.gmail.insta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.Following;

public interface FollowingRepository extends JpaRepository<Following, Long> {
		
	Optional<Following> findByFollowerAndUser(Long follower, Long user);
	List<Following> findByFollower(Long id);	

}
