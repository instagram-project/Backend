package com.gmail.insta.repository;

import com.gmail.insta.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findById(long id);

    @Transactional
    void deleteById(Long id);

}
