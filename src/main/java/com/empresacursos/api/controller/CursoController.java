package com.empresacursos.api.controller;

import com.empresacursos.api.dto.CursoRequestDTO;
import com.empresacursos.api.dto.CursoResponseDTO;
import com.empresacursos.api.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController // Marca esta classe como um controller REST
@RequestMapping("/cursos") // Mapeia todas as requisições para /cursos para este controller
public class CursoController {

    @Autowired // Injeta a dependência do CursoService
    private CursoService cursoService;

    // POST - /cursos
    @PostMapping
    public ResponseEntity<CursoResponseDTO> createCurso(@RequestBody CursoRequestDTO cursoRequestDTO) {
        CursoResponseDTO novoCurso = cursoService.createCurso(cursoRequestDTO);
        return new ResponseEntity<>(novoCurso, HttpStatus.CREATED);
    }

    // GET - /cursos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> getAllCursos(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        List<CursoResponseDTO> cursos = cursoService.getAllCursos(name, category);
        return ResponseEntity.ok(cursos);
    }

    // GET - /cursos/{id} (Rota adicional útil para buscar um curso específico)
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> getCursoById(@PathVariable UUID id) {
        try {
            CursoResponseDTO curso = cursoService.getCursoById(id);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) { // Tratar exceção de curso não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // PUT - /cursos/:id
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> updateCurso(@PathVariable UUID id, @RequestBody CursoRequestDTO cursoRequestDTO) {
        try {
            CursoResponseDTO cursoAtualizado = cursoService.updateCurso(id, cursoRequestDTO);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (RuntimeException e) { // Tratar exceção de curso não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - /cursos/:id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable UUID id) {
        try {
            cursoService.deleteCurso(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (RuntimeException e) { // Tratar exceção de curso não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH - /cursos/:id/active
    @PatchMapping("/{id}/active")
    public ResponseEntity<CursoResponseDTO> toggleActiveStatus(@PathVariable UUID id) {
        try {
            CursoResponseDTO curso = cursoService.toggleActiveStatus(id);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) { // Tratar exceção de curso não encontrado
            return ResponseEntity.notFound().build();
        }
    }
}