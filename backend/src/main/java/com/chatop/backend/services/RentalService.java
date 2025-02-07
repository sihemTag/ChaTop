package com.chatop.backend.services;

import com.chatop.backend.dto.RentalResponse;
import com.chatop.backend.dto.RentalsResponse;
import com.chatop.backend.entities.RentalEntity;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exceptions.NotFoundException;
import com.chatop.backend.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    @Value("${image.directory}")
    String imageDir;

    public RentalService(RentalRepository rentalRepository, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
    }

    public RentalsResponse getAll(){
        List<RentalEntity> rentals = rentalRepository.findAll();
        List<RentalResponse> rentalsList = new ArrayList<>();
        for(RentalEntity rental: rentals){
            RentalResponse ren = new RentalResponse();
            ren.setName(rental.getName());
            ren.setDescription(rental.getDescription());
            ren.setPrice(rental.getPrice());
            ren.setCreatedAt(rental.getCreatedAt());
            ren.setPicture("http://localhost:3001/api/rentals/image/" + rental.getPicture().substring(rental.getPicture().lastIndexOf("/") + 1));
            ren.setSurface(rental.getSurface());
            ren.setOwnerId(rental.getOwner().getId());
            ren.setUpdatedAt(rental.getUpdatedAt());
            ren.setId(rental.getId());
            rentalsList.add(ren);
        }
        return new RentalsResponse(rentalsList);
    }

    public RentalResponse getRentalById(Integer id) throws NotFoundException {
       RentalEntity rental =  rentalRepository.findById(id).orElseThrow(() -> new NotFoundException("rental not found!"));
       RentalResponse ren = new RentalResponse();
        ren.setName(rental.getName());
        ren.setDescription(rental.getDescription());
        ren.setPrice(rental.getPrice());
        ren.setCreatedAt(rental.getCreatedAt());
        ren.setPicture("http://localhost:3001/api/rentals/image/" + rental.getPicture().substring(rental.getPicture().lastIndexOf("/") + 1));
        ren.setSurface(rental.getSurface());
        ren.setOwnerId(rental.getOwner().getId());
        ren.setUpdatedAt(rental.getUpdatedAt());
        ren.setId(rental.getId());

        return ren;
    }

    public void createRental(String name, Float surface, Float price, String description, MultipartFile file, Authentication authentication){
        try {
            if (file.isEmpty()) {
                return;
            }

            Path uploadPath = Paths.get(imageDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Sauvegarder le fichier sur le serveur
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());

            String fileUrl = uploadPath.resolve(file.getOriginalFilename()).toString().replace("\\", "/");

            RentalEntity rental = new RentalEntity();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setCreatedAt(LocalDate.now());
            rental.setPicture(fileUrl);

            UserEntity owner = userService.currentUserName(authentication).orElseThrow(() -> new IllegalArgumentException("No user authenticated found!"));
            rental.setOwner(owner);

            rentalRepository.save(rental);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRental(Integer id, String name, Float surface, Float price, String description) throws NotFoundException{
        RentalEntity currentRental = rentalRepository.findById(id).orElseThrow(()-> new NotFoundException("Rental doesn't exist!"));

        currentRental.setName(name);
        currentRental.setDescription(description);
        currentRental.setPrice(price);
        currentRental.setSurface(surface);
        currentRental.setUpdatedAt(LocalDate.now());

        rentalRepository.save(currentRental);
    }

    public void deleteRental(Integer id){
        rentalRepository.deleteById(id);
    }

}
