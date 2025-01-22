package com.projeto.projeto.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Obra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idObra;

    @NotNull(message = "Nome da obra não pode ser nulo!")
    @NotBlank(message = "Nome da obra não pode ser branco!")
    @Length(max = 50, message = "Nome da obra não pode exceder 50 caracteres!")
    private String titulo;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dataLancamento;

    //RELACIONAMENTOS

    // obra -> categoria
    @ManyToOne
    @JoinColumn(name = "id_Categoria")
    private Categoria categoriaObra;

    // obra <-> local
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "obra_local",
            joinColumns = @JoinColumn(name = "id_obra"),
            inverseJoinColumns = @JoinColumn(name = "id_local"),
            uniqueConstraints = @UniqueConstraint(name = "obra_local_unique", columnNames = {"id_obra","id_local"})
    )
    @JsonIgnoreProperties({"obras"})
    private List<Local> locais;

    // obra <-> personagem
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "obra_personagem",
            joinColumns = @JoinColumn(name = "id_obra"),
            inverseJoinColumns = @JoinColumn(name = "id_personagem"),
            uniqueConstraints = @UniqueConstraint(name = "obra_personagem_unique", columnNames = {"id_obra","id_personagem"})
    )
    @JsonIgnoreProperties({"obras"})
    private List<Personagem> personagens;
}