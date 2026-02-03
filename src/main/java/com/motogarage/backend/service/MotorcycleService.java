package com.motogarage.backend.service;

import com.motogarage.backend.dto.MotorcycleDTO;
import com.motogarage.backend.mapper.EntityMapper;
import com.motogarage.backend.model.Motorcycle;
import com.motogarage.backend.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final EntityMapper entityMapper;

    public List<MotorcycleDTO> findAll() {
        return motorcycleRepository.findAll().stream()
                .map(entityMapper::toMotorcycleDTO)
                .toList();
    }

    public MotorcycleDTO findById(Long id) {
        Motorcycle moto = motorcycleRepository.findById(id)
                .orElse(null);
        return (moto != null) ? entityMapper.toMotorcycleDTO(moto) : null;
    }

    public MotorcycleDTO save(Motorcycle motorcycle) {
        Motorcycle saved = motorcycleRepository.save(motorcycle);
        return entityMapper.toMotorcycleDTO(saved);
    }

    public void delete(Long id) {
        if (!motorcycleRepository.existsById(id)) {
            throw new RuntimeException("Motorcycle not found with id: " + id);
        }
        motorcycleRepository.deleteById(id);
    }

    public MotorcycleDTO update(Long id, Motorcycle motorcycleDetails) {
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