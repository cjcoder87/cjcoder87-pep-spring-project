package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

// will needs to change the type possibly
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    // List<Message> findMessageByAccountId(Integer accountId);
}
