package com.chatop.backend.services;

import com.chatop.backend.entities.MessageEntity;
import com.chatop.backend.entities.RentalEntity;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exceptions.NotFoundException;
import com.chatop.backend.repositories.MessageRepository;
import com.chatop.backend.repositories.RentalRepository;
import com.chatop.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public void addMessage(String message, Integer rentalId, Integer userId) throws NotFoundException {
        RentalEntity rentalEntity = rentalRepository.findById(rentalId).orElseThrow(()->new NotFoundException("Rental not found!"));
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new NotFoundException("User not found!"));
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        messageEntity.setUser(userEntity);
        messageEntity.setRental(rentalEntity);
        messageEntity.setCreatedAt(LocalDate.now());

        messageRepository.save(messageEntity);
    }

    public void deleteById(Integer id){
        messageRepository.deleteById(id);
    }
}

