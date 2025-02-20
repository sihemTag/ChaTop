package com.chatop.backend.controllers;

import com.chatop.backend.configuration.TokenProvider;
import com.chatop.backend.dto.RegisterDTO;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exceptions.UserAlreadyExistException;
import com.chatop.backend.services.IUserService;
import com.chatop.backend.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final IUserService iUserService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public UserController(UserService userService, IUserService iUserService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.iUserService = iUserService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Operation(summary = "registers a user", description = "returns a token after successfull registration")
    @PostMapping("/api/auth/register")
    public ResponseEntity<JWTToken> register(@RequestBody RegisterDTO registerDto) throws UserAlreadyExistException {
        iUserService.save(registerDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                registerDto.getEmail(), registerDto.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "login a user", description = "returns a token after successfull login")
    @PostMapping("/api/auth/login")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginRequest loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "get the current authenticated user", description = "returns the current user")
    @GetMapping("/api/auth/me")
    public Optional<UserEntity> currentUserName(Authentication authentication) {
        return userService.currentUserName(authentication);
    }

    @GetMapping("/api/user/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }


    static class JWTToken {

        private String idToken;

        public JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }


}
