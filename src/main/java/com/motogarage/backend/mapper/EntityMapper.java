package com.motogarage.backend.mapper;

import com.motogarage.backend.dto.*;
import com.motogarage.backend.model.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public UserDTO toUserDTO(User entity) {
        if (entity == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPhotoUrl(entity.getPhotoUrl());

        if (entity.getMyMotorcycles() != null) {
            dto.setMyMotorcycles(entity.getMyMotorcycles().stream()
                    .map(this::toMotorcycleDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public ModDTO toModDTO(Mod entity) {
        if (entity == null) return null;

        ModDTO dto = new ModDTO();
        dto.setId(entity.getId());
        dto.setNamePiece(entity.getNamePiece());
        dto.setBrandPiece(entity.getBrandPiece());
        dto.setUrlShop(entity.getUrlShop());

        if (entity.getMotorcycle() != null) {
            dto.setMotorcycleId(entity.getMotorcycle().getId());
        }
        return dto;
    }

    public MotorcycleDTO toMotorcycleDTO(Motorcycle entity) {
        if (entity == null) return null;

        MotorcycleDTO dto = new MotorcycleDTO();
        dto.setId(entity.getId());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setYear(entity.getYear());
        dto.setDescription(entity.getDescription());
        dto.setPhotoUrl(entity.getPhotoUrl());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        if (entity.getMods() != null) {
            dto.setMods(entity.getMods().stream()
                    .map(this::toModDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}