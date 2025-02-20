package com.chatop.backend.controllers;

import com.chatop.backend.dto.ResponseMessage;
import com.chatop.backend.exceptions.NotFoundException;
import com.chatop.backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "sends a message to an owner")
    @PostMapping
    public ResponseEntity<ResponseMessage> addMessage(@RequestBody MessageRequest message) throws NotFoundException {
        messageService.addMessage(message.getMessage(), message.getRentalId(), message.getUserId());
        ResponseMessage responseMessage = new ResponseMessage("Message send with success !");
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
}
