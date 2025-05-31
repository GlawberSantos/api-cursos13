package com.empresacursos.api.repository;

import com.empresacursos.api.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository // Marca esta interface como um componente de repositório Spring
public interface CursoRepository extends JpaRepository<Curso, UUID> {

    // Método para buscar cursos por nome e/ou categoria (case-insensitive)
    // Spring Data JPA cria a implementação automaticamente com base no nome do método.
    List<Curso> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
    List<Curso> findByNameContainingIgnoreCase(String name);
    List<Curso> findByCategoryContainingIgnoreCase(String category);
}
