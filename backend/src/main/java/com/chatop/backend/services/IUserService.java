package com.chatop.backend.services;

import com.chatop.backend.dto.RegisterDTO;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exceptions.UserAlreadyExistException;

public interface IUserService {
    UserEntity save(RegisterDTO registerDto) throws UserAlreadyExistException;
}
