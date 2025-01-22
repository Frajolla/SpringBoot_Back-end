package com.projeto.projeto.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projeto.model.Ator;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Obra;
import com.projeto.projeto.model.Personagem;
import com.projeto.projeto.Repository.AtorRepository;
import com.projeto.projeto.Repository.PersonagemRepository;
import com.projeto.projeto.exception.NoSuchElementException;

import java.util.List;

@Service
public class PersonagemService {

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private AtorRepository atorRepository;

    // GET ALL
    public List<Personagem> findAll() {
        return personagemRepository.findAll();
    }

    //GET BY ID
    public Personagem findById(Integer pIdPersonagem) {
        Personagem vPersonagem = personagemRepository
                .findById(pIdPersonagem)
                .orElseThrow(
                    () -> new com.projeto.projeto.exception.NoSuchElementException("Personagem " + pIdPersonagem + " não encontrado!")
                );

        return vPersonagem;
    }
    // POST
    public Personagem inserePersonagem(Personagem vPersonagem) {
        return personagemRepository.save(vPersonagem);
    }
    // POST JOIN
    public Personagem insAtorPersonagem(Integer pIdPersonagem, Integer pIdAtor) {
        Ator vAtor = atorRepository
            .findById(pIdAtor)
            .orElseThrow();

        Personagem vPersonagem = personagemRepository
            .findById(pIdPersonagem)
            .orElseThrow();
            
        List<Ator> vAtores = vPersonagem.getAtores();
        vAtores.add(vAtor);
        vPersonagem.setAtores(vAtores);

        try {
            vPersonagem = personagemRepository.save(vPersonagem);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new com.projeto.projeto.exception.DataIntegrityViolationException( e.getMessage() +
                "Ator "+vAtor.getNome()+" já está inserido no personagem "+vPersonagem.getNome()+"!");
        }
        return vPersonagem;
    }

    // PUT
    public Personagem updPersonagem(Integer pIdPersonagem, Personagem pPersonagem) {
        Personagem vPersonagem = personagemRepository
                .findById(pIdPersonagem)
                .orElseThrow(
                    () -> new com.projeto.projeto.exception.NoSuchElementException("Personagem " + pIdPersonagem + " não encontrado!")
                );

        if (pPersonagem.getCaracteristicas() != null) {
            vPersonagem.setCaracteristicas(pPersonagem.getCaracteristicas());
        }

        if (pPersonagem.getNome() != null) {
            vPersonagem.setNome(pPersonagem.getNome());
        }

        personagemRepository.save(vPersonagem);
        return vPersonagem;
    }

    // DELETE
    public ResponseEntity<MensagemDTO> delPersonagem(Integer pIdPersonagem){
        try {
            Personagem vPersonagem = personagemRepository
                .findById(pIdPersonagem)
                .orElseThrow(() -> new com.projeto.projeto.exception.NoSuchElementException(
                    "Personagem " + pIdPersonagem + " não encontrado!"));

            // Removendo relacionamentos
             for (Obra obra : vPersonagem.getObras()){
                 obra.getPersonagens().remove(vPersonagem);
             }

            vPersonagem.getObras().clear();
            
            personagemRepository.delete(vPersonagem);
            return ResponseEntity.ok().body(new MensagemDTO("OK", "OK"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("ERRO", e.getMessage())); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemDTO("ERRO", e.getMessage())); 
        }
    }
}