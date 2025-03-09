package com.chatop.backend.controllers;

import com.chatop.backend.dto.RentalResponse;
import com.chatop.backend.dto.RentalsResponse;
import com.chatop.backend.dto.ResponseMessage;
import com.chatop.backend.exceptions.NotFoundException;
import com.chatop.backend.services.RentalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public RentalsResponse getAll(){
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public RentalResponse getRentalById(@PathVariable Integer id) throws NotFoundException {
        return rentalService.getRentalById(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Float surface,
            @RequestParam("price") Float price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile file,
            Authentication authentication
    ){
        ResponseMessage responseMessage = new ResponseMessage();
            rentalService.createRental(name, surface,price, description,file,authentication);
            responseMessage.setMessage("Rental created !");
            return  new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> updateRental(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("surface") Float surface,
            @RequestParam("price") Float price,
            @RequestParam("description") String description
    ) throws NotFoundException {
        ResponseMessage responseMessage = new ResponseMessage("Rental updated !");
        rentalService.updateRental(id,name, surface,price,description);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteRentalById(@PathVariable Integer id){
        rentalService.deleteRental(id);
    }

}
