package com.projeto.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Categoria implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idCategoria;

    @NotNull(message = "Nome da Categoria não pode ser nulo!")
    @NotBlank(message = "Nome da Categoria não pode ser branco!")
    @Length(min = 4, max = 50, message = "Nome da categoria deve ter entre 4 a 50 caracteres")
    private String nomeCategoria;

    @JsonIgnore
    @OneToMany(mappedBy="categoriaObra")
    private List<Obra> obras = new ArrayList<>();
}