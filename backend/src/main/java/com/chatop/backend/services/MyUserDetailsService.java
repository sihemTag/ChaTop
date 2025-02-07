package com.chatop.backend.services;

import com.chatop.backend.entities.Role;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(getRoles(user))
                    .build();

        }else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(UserEntity user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return new String[]{"USER"};
        }
        return user.getRoles().stream()
                .map(Role::getName)
                .toArray(String[]::new);
    }

}
