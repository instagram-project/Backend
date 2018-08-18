package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.insta.model.Like;
import com.gmail.insta.repository.LikeRepository;


@Service
public class LikeServiceImpl implements LikeService{

	private final LikeRepository likeRepository;

	@Autowired
	public LikeServiceImpl(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}

	@Override
	public List<Long> findUsersIdByMessage(Long id){
		List<Like> likes = likeRepository.findByMessage(id);
		return likes.stream().map(Like::getUser).collect(Collectors.toList());
	}

	@Override
	public Optional<Like> findLikeByUserAndMessage(Long user, Long id){
		return likeRepository.findByUserAndMessage(user, id);
	}

	@Override
	public void add(Like like) {
		likeRepository.save(like);
	}

	@Override
	@Transactional
	public void deleteByUserAndMessage(Long userid, Long messageid) {
		likeRepository.deleteByUserAndMessage(userid, messageid);
	}

}
