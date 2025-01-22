package com.projeto.projeto.model;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Ator implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAtor;

    @NotNull(message = "Nome do ator não pode ser nulo!")
    @NotBlank(message = "Nome do ator não pode ser branco!")
    private String nome;

    @NotNull(message = "Idade não pode ser nulo!")
    @Range(min = 0, max = 115, message = "Idade deve ser um valor entre 0 e 115!")
    private Integer idade;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "atores") // Cascade para deletar o relacionamento Ator <-> Personagem
    @JsonIgnoreProperties({"atores"}) // Evitando loop
    private List<Personagem> personagens;
}