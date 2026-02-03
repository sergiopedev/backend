package com.motogarage.backend.service;

import com.motogarage.backend.dto.UserDTO;
import com.motogarage.backend.model.User;
import com.motogarage.backend.mapper.EntityMapper;
import com.motogarage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(entityMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(entityMapper::toUserDTO)
                .orElse(null);
    }

    public UserDTO save(User user) {
        return entityMapper.toUserDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserDTO update(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            existingUser.setPassword(userDetails.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        return entityMapper.toUserDTO(updatedUser);
    }
}