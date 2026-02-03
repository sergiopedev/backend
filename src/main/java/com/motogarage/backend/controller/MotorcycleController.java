package com.motogarage.backend.controller;

import com.motogarage.backend.dto.MotorcycleDTO;
import com.motogarage.backend.model.Motorcycle;
import com.motogarage.backend.service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping
    public ResponseEntity<List<MotorcycleDTO>> getAll() {
        return ResponseEntity.ok(motorcycleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> getById(@PathVariable Long id) {
        MotorcycleDTO moto = motorcycleService.findById(id);
        if (moto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<MotorcycleDTO> create(@Valid @RequestBody Motorcycle motorcycle) {
        return new ResponseEntity<>(motorcycleService.save(motorcycle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> update(@PathVariable Long id, @Valid @RequestBody Motorcycle motorcycle) {
        return ResponseEntity.ok(motorcycleService.update(id, motorcycle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motorcycleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}