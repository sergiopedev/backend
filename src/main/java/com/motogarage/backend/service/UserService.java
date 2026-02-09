package com.motogarage.backend.service;

import com.motogarage.backend.dto.UserDTO;
import com.motogarage.backend.model.User;
import com.motogarage.backend.mapper.EntityMapper;
import com.motogarage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(entityMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(entityMapper::toUserDTO)
                .orElse(null);
    }

    @Transactional
    public UserDTO save(User user) {
        return entityMapper.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(existing);
    }

    @Transactional
    public UserDTO update(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhotoUrl(userDetails.getPhotoUrl());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            existingUser.setPassword(userDetails.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        return entityMapper.toUserDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return entityMapper.toUserDTO(user);
    }
}