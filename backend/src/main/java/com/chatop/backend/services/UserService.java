package com.chatop.backend.services;

import com.chatop.backend.dto.RegisterDTO;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exceptions.UserAlreadyExistException;
import com.chatop.backend.repositories.RoleRepository;
import com.chatop.backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(RegisterDTO registerDto) throws UserAlreadyExistException {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UserAlreadyExistException(" user already exist "+registerDto.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreatedAt(LocalDate.now());

        roleRepository.findByName("USER")
                .ifPresent(r -> user.setRoles(Collections.singletonList(r)));
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public Optional<UserEntity> currentUserName(Authentication authentication) {
        String name = authentication.getName();
        return getUserByEmail(name);
    }


}
