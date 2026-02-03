package com.motogarage.backend.controller;

import com.motogarage.backend.dto.ModDTO;
import com.motogarage.backend.model.Mod;
import com.motogarage.backend.service.ModService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mods")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ModController {

    private final ModService modService;

    @GetMapping
    public ResponseEntity<List<ModDTO>> getAll() {
        return ResponseEntity.ok(modService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModDTO> getById(@PathVariable Long id) {
        ModDTO mod = modService.findById(id);
        if (mod == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mod);
    }

    @PostMapping
    public ResponseEntity<ModDTO> create(@Valid @RequestBody Mod mod) {
        return new ResponseEntity<>(modService.save(mod), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModDTO> update(@PathVariable Long id, @Valid @RequestBody Mod mod) {
        return ResponseEntity.ok(modService.update(id, mod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modService.delete(id);
        return ResponseEntity.noContent().build();
    }
}