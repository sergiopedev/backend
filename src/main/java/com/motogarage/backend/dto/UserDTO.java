package com.motogarage.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String photoUrl;
    private List<MotorcycleDTO> myMotorcycles;
}