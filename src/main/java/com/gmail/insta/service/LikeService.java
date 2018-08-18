package com.gmail.insta.service;

import java.util.List;
import java.util.Optional;


import com.gmail.insta.model.Like;



public interface LikeService {

    List<Long> findUsersIdByMessage(Long id);

    Optional<Like> findLikeByUserAndMessage(Long userid, Long id);

    void add(Like like);

    void deleteByUserAndMessage(Long userid, Long messageId);

}
