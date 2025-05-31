package com.empresacursos.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data; // Se estiver usando Lombok
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Marca esta classe como uma entidade JPA
@Data // Lombok: Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Gera construtor sem argumentos
@AllArgsConstructor // Lombok: Gera construtor com todos os argumentos
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Gera o ID automaticamente
    private UUID id;

    @Column(nullable = false) // Não pode ser nulo
    private String name;

    @Column(nullable = false) // Não pode ser nulo
    private String category;

    private boolean active = true; // Valor padrão ao criar, pode ser alterado depois

    @CreationTimestamp // Define automaticamente a data de criação
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Define automaticamente a data de atualização
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Se não usar Lombok, você precisará criar manualmente:
    // - Construtores
    // - Getters e Setters para todos os campos
    // - toString(), equals(), hashCode() (opcional, mas bom para debug)
}