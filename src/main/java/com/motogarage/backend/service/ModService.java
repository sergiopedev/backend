package com.motogarage.backend.service;

import com.motogarage.backend.dto.ModDTO;
import com.motogarage.backend.model.Mod;
import com.motogarage.backend.mapper.EntityMapper;
import com.motogarage.backend.model.Motorcycle;
import com.motogarage.backend.repository.ModRepository;
import com.motogarage.backend.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModService {

    private final ModRepository modRepository;
    private final MotorcycleRepository motorcycleRepository; // Necesitamos este
    private final EntityMapper entityMapper;

    public ModDTO save(Mod mod) {
        Motorcycle moto = motorcycleRepository.findById(mod.getMotorcycle().getId())
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));
        mod.setMotorcycle(moto);
        Mod saved = modRepository.save(mod);
        return entityMapper.toModDTO(saved);
    }

    public List<ModDTO> findAll() {
        return modRepository.findAll().stream()
                .map(entityMapper::toModDTO)
                .toList();
    }

    public ModDTO findById(Long id) {
        Mod mod = modRepository.findById(id)
                .orElse(null);
        return (mod != null) ? entityMapper.toModDTO(mod) : null;
    }

    public void delete(Long id) {
        if (!modRepository.existsById(id)) {
            throw new RuntimeException("Mod not found with id: " + id);
        }
        modRepository.deleteById(id);
    }

    public ModDTO update(Long id, Mod modDetails) {
        Mod existingMod = modRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mod not found with id: " + id));

        existingMod.setNamePiece(modDetails.getNamePiece());
        existingMod.setBrandPiece(modDetails.getBrandPiece());
        existingMod.setUrlShop(modDetails.getUrlShop());

        Mod updatedMod = modRepository.save(existingMod);
        return entityMapper.toModDTO(updatedMod);
    }
}