package com.projeto.projeto.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Personagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersonagem;

    @NotNull(message = "Nome do personagem não pode ser nulo!")
    @NotBlank(message = "Nome do personagem não pode ser branco!")
    private String nome;

    private String caracteristicas;

    // Many to many: Personagem <-> Ator
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "personagem_ator",
        joinColumns = @JoinColumn(name = "id_personagem"),
        inverseJoinColumns = @JoinColumn(name = "id_ator"),
        uniqueConstraints = @UniqueConstraint(
            name = "personagem_ator_unique",
            columnNames = {"id_personagem", "id_ator"})
    )
    @JsonIgnoreProperties({"personagens"}) // Evitando loop
    private List<Ator> atores;


    @ManyToMany(mappedBy = "personagens", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties({"personagens"})
    private List<Obra> obras;

}