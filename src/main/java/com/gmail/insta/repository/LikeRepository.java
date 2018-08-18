package com.gmail.insta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.insta.model.Like;


public interface LikeRepository extends JpaRepository<Like, Long>{
	
    List<Like> findByMessage(Long id);

	Optional<Like> findByUserAndMessage(Long userId, Long messageId);

	void deleteByUserAndMessage(Long userId, Long messageId);

}
