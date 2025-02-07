package com.chatop.backend.repositories;

import com.chatop.backend.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity,Integer> {
}