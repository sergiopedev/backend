package com.motogarage.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class MotorcycleDTO {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String description;
    private String photoUrl;
    private Long userId;
    private List<ModDTO> mods;
}