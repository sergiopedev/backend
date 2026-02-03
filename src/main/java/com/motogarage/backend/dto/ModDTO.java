package com.motogarage.backend.dto;

import lombok.Data;

@Data
public class ModDTO {
    private Long id;
    private String namePiece;
    private String brandPiece;
    private String urlShop;
    private Long motorcycleId;
}