package com.example.kameleoon.service;

import com.example.kameleoon.dto.CreateUserDto;
import com.example.kameleoon.dto.UpdateUserDto;
import com.example.kameleoon.entity.User;
import com.example.kameleoon.exceptions.UserException;
import com.example.kameleoon.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Transactional
    public User createUser(CreateUserDto createUserDto) {
        Optional<User> userOptional = userRepository.findByEmail(createUserDto.getEmail());
        if (userOptional.isPresent()) {
            throw new UserException("user by this emil already exist");
        }
        try {
            return userRepository.save(new User(createUserDto.getUserName(), createUserDto.getEmail(),
//                    passwordEncoder.encode(createUserDto.getPassword())
                    createUserDto.getPassword()
            ));
        } catch (Exception e) {
            log.error("can not create user {}", e.getMessage(), e);
            return null;
        }
    }

    @Transactional
    public Optional<User> updateUser(String email, UpdateUserDto updateUserDto) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        boolean updated = false;
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        if (!StringUtils.isBlank(updateUserDto.getUserName())) {
            user.setUserName(updateUserDto.getUserName());
            updated = true;
        }
        if (!StringUtils.isBlank(updateUserDto.getEmail()) && !userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
            user.setEmail(updateUserDto.getEmail());
            updated = true;
        }
        if (!StringUtils.isBlank(updateUserDto.getPassword())) {
//            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setPassword(updateUserDto.getPassword());
            updated = true;
        }
        if (!updated) {
            return Optional.of(user);
        }
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (EntityNotFoundException e) {
            log.error("user by this email not found {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (EntityNotFoundException e) {
            log.error("user by this id not found {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.findByEmail(email).ifPresent(userRepository::delete);
    }
}
