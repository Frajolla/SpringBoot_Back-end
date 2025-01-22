package com.projeto.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Local implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLocal;

    @NotNull(message = "Nome do local não pode ser nulo!")
    @NotBlank(message = "Nome do local não pode ser branco!")
    @Length(min = 3, max = 50, message = "Nome do local deve estar entre 3 e 255 caracteres!")
    private String nmLocal;

    @Length(max = 255, message = "História local não pode ser maior que 255 caracteres")
    private String historiaLocal;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "locais")
    @JsonIgnoreProperties({"atores"})
    private List<Obra> obras = new ArrayList<>();
}