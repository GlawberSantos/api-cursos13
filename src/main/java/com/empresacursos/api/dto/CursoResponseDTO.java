package com.empresacursos.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class CursoResponseDTO {
    private UUID id;
    private String name;
    private String category;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}