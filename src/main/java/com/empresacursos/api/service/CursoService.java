package com.empresacursos.api.service;

import com.empresacursos.api.dto.CursoRequestDTO;
import com.empresacursos.api.dto.CursoResponseDTO;
import com.empresacursos.api.model.Curso;
import com.empresacursos.api.repository.CursoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // Marca esta classe como um componente de serviço Spring
public class CursoService {

    @Autowired // Injeta a dependência do CursoRepository
    private CursoRepository cursoRepository;

    private CursoResponseDTO convertToDTO(Curso curso) {
        CursoResponseDTO dto = new CursoResponseDTO();
        BeanUtils.copyProperties(curso, dto); // Copia propriedades com nomes iguais
        return dto;
    }

    @Transactional // Garante que a operação seja atômica
    public CursoResponseDTO createCurso(CursoRequestDTO cursoRequestDTO) {
        Curso curso = new Curso();
        curso.setName(cursoRequestDTO.getName());
        curso.setCategory(cursoRequestDTO.getCategory());
        // 'id', 'createdAt', 'updatedAt' são gerados automaticamente
        // 'active' tem valor padrão true definido na entidade
        Curso savedCurso = cursoRepository.save(curso);
        return convertToDTO(savedCurso);
    }

    @Transactional(readOnly = true) // Operação de leitura
    public List<CursoResponseDTO> getAllCursos(String name, String category) {
        List<Curso> cursos;
        if (name != null && !name.isEmpty() && category != null && !category.isEmpty()) {
            cursos = cursoRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
        } else if (name != null && !name.isEmpty()) {
            cursos = cursoRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null && !category.isEmpty()) {
            cursos = cursoRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            cursos = cursoRepository.findAll();
        }
        return cursos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO getCursoById(UUID id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id)); // Lançar exceção mais específica em produção
        return convertToDTO(curso);
    }

    @Transactional
    public CursoResponseDTO updateCurso(UUID id, CursoRequestDTO cursoRequestDTO) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));

        boolean updated = false;
        if (cursoRequestDTO.getName() != null && !cursoRequestDTO.getName().isEmpty()) {
            cursoExistente.setName(cursoRequestDTO.getName());
            updated = true;
        }
        if (cursoRequestDTO.getCategory() != null && !cursoRequestDTO.getCategory().isEmpty()) {
            cursoExistente.setCategory(cursoRequestDTO.getCategory());
            updated = true;
        }

        // 'updatedAt' será atualizado automaticamente pelo @UpdateTimestamp se houver mudança
        // O campo 'active' não é atualizado aqui.
        if (updated) {
            Curso updatedCurso = cursoRepository.save(cursoExistente);
            return convertToDTO(updatedCurso);
        }
        return convertToDTO(cursoExistente); // Retorna o existente se nada mudou
    }

    @Transactional
    public void deleteCurso(UUID id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado com id: " + id);
        }
        cursoRepository.deleteById(id);
    }

    @Transactional
    public CursoResponseDTO toggleActiveStatus(UUID id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
        curso.setActive(!curso.isActive()); // Inverte o status
        Curso updatedCurso = cursoRepository.save(curso);
        return convertToDTO(updatedCurso);
    }
}