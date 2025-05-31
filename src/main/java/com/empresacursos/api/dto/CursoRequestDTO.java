package com.empresacursos.api.dto;

import lombok.Data;

@Data
public class CursoRequestDTO {
    private String name;
    private String category;
    // O campo 'active' não é incluído aqui para POST e PUT,
    // pois será tratado por uma rota PATCH específica ou terá um valor padrão.
}