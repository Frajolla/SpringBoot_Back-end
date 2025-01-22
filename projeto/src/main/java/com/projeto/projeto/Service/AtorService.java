package com.projeto.projeto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projeto.Repository.AtorRepository;
import com.projeto.projeto.exception.NoSuchElementException;
import com.projeto.projeto.model.Ator;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Personagem;

@Service
public class AtorService {

    @Autowired
    private AtorRepository atorRepository;

    // GET ALL
    public List<Ator> findAll(){
        return atorRepository.findAll();
    }

    // GET
    public Ator findById(Integer pIdAtor) {
        Ator vAtor = atorRepository
            .findById(pIdAtor)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Ator " + pIdAtor + " não encontrado!")
            );
        
        return vAtor;
    }

    // POST
    public Ator insAtor(Ator pAtor) {
        pAtor.setIdAtor(null);
        return atorRepository.save(pAtor);
    }

    // PUT
    public Ator updAtor(Integer pIdAtor, Ator pAtor){
        Ator vAtorAtual = atorRepository
            .findById(pIdAtor)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Ator "+pIdAtor+" não encontrado!")
                );

        if (pAtor.getNome() != null) {
            vAtorAtual.setNome(pAtor.getNome());
        }
        if (pAtor.getIdade() != null) {
            vAtorAtual.setIdade(pAtor.getIdade());
        }

        return atorRepository.save(vAtorAtual);
    }

    // DELETE
    public ResponseEntity<MensagemDTO> delAtor(Integer pIdAtor) {
        try {
            Ator vAtor = atorRepository
                .findById(pIdAtor)
                .orElseThrow(
                    () -> new com.projeto.projeto.exception.NoSuchElementException(
                    "Ator " + pIdAtor + " não encontrado!")
                );

            for(Personagem personagem : vAtor.getPersonagens()){
                personagem.getAtores().remove(vAtor);
            }

            vAtor.getPersonagens().clear();

            atorRepository.delete(vAtor);
            return ResponseEntity.ok().body(new MensagemDTO("OK", "OK"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("ERRO", e.getMessage())); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemDTO("ERRO", e.getMessage())); 
        }
    }
}