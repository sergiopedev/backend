package com.motogarage.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@Table(name = "motorcycles")
@Data
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year cannot be earlier than 1900")
    @Max(value = 2026, message = "Year is invalid")
    private Integer year;

    @Size(max = 255, message = "Description is too long")
    private String description;

    @NotBlank(message = "Photo URL is required")
    @URL(message = "Photo URL must be valid")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "motorcycle", cascade = CascadeType.ALL)
    private List<Mod> mods;
}