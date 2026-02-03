package com.motogarage.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "mods")
@Data
public class Mod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Part name is required")
    private String namePiece;

    @NotBlank(message = "Part brand is required")
    private String brandPiece;

    @NotBlank(message = "URL is required")
    @URL(message = "Shop URL must be valid")
    private String urlShop;

    @ManyToOne
    @JoinColumn(name = "motorcycle_id", nullable = false)
    private Motorcycle motorcycle;
}