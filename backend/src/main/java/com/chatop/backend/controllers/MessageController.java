package com.chatop.backend.controllers;

import com.chatop.backend.dto.ResponseMessage;
import com.chatop.backend.exceptions.NotFoundException;
import com.chatop.backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "sends a message to an owner")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> addMessage(@RequestBody MessageRequest message) throws NotFoundException {
        try {
            messageService.addMessage(message.getMessage(), message.getRentalId(), message.getUserId());
            ResponseMessage responseMessage = new ResponseMessage("Message send with success !");
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteById(@PathVariable Integer id){
        messageService.deleteById(id);
    }
}
