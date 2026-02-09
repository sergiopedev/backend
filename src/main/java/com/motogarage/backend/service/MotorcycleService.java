package com.motogarage.backend.service;

import com.motogarage.backend.dto.MotorcycleDTO;
import com.motogarage.backend.mapper.EntityMapper;
import com.motogarage.backend.model.Motorcycle;
import com.motogarage.backend.model.User;
import com.motogarage.backend.repository.MotorcycleRepository;
import com.motogarage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    @Transactional(readOnly = true)
    public List<MotorcycleDTO> findAll() {
        return motorcycleRepository.findAll().stream()
                .map(entityMapper::toMotorcycleDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MotorcycleDTO findById(Long id) {
        Motorcycle moto = motorcycleRepository.findById(id)
                .orElse(null);
        return (moto != null) ? entityMapper.toMotorcycleDTO(moto) : null;
    }

    @Transactional
    public MotorcycleDTO save(MotorcycleDTO dto) {
        if (dto.getUserId() == null) {
            throw new RuntimeException("userId es requerido");
        }

        User owner = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        Motorcycle entity = new Motorcycle();
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setDescription(dto.getDescription());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setUser(owner);

        Motorcycle saved = motorcycleRepository.save(entity);
        return entityMapper.toMotorcycleDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        Motorcycle existing = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found with id: " + id));
        motorcycleRepository.delete(existing);
    }

    @Transactional
    public MotorcycleDTO update(Long id, MotorcycleDTO motorcycleDetails) {
        Motorcycle existingMoto = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found with id: " + id));

        existingMoto.setBrand(motorcycleDetails.getBrand());
        existingMoto.setModel(motorcycleDetails.getModel());
        existingMoto.setYear(motorcycleDetails.getYear());
        existingMoto.setDescription(motorcycleDetails.getDescription());
        existingMoto.setPhotoUrl(motorcycleDetails.getPhotoUrl());

        Motorcycle updatedMoto = motorcycleRepository.save(existingMoto);
        return entityMapper.toMotorcycleDTO(updatedMoto);
    }
}