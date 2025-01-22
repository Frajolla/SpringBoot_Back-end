package com.projeto.projeto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projeto.Repository.CategoriaRepository;
import com.projeto.projeto.exception.NoSuchElementException;
import com.projeto.projeto.model.Categoria;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Obra;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;


     // GET ALL
    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    // GET
    public Categoria findById(Integer pIdCategoria) {
        Categoria vCategoria = categoriaRepository
        .findById(pIdCategoria)
        .orElseThrow(
            () -> new com.projeto.projeto.exception.NoSuchElementException("Categoria " + pIdCategoria + " não encontrada!")
        );

        return vCategoria;
    }

    // POST
    public Categoria insCategoria(Categoria pCategoria){
        pCategoria.setIdCategoria(null);
        return categoriaRepository.save(pCategoria);
    }

    // PUT
    public Categoria updCategoria(Integer pIdCategoria, Categoria pCategoria){
        Categoria vCategoria = categoriaRepository
            .findById(pIdCategoria)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Categoria " + pIdCategoria + " não encontrada!")
            );
        
        if (pCategoria.getNomeCategoria() != null) {
            vCategoria.setNomeCategoria(pCategoria.getNomeCategoria());
        }

        return categoriaRepository.save(vCategoria);
    }

    // DELETE
    public ResponseEntity<MensagemDTO> delCategoria(Integer pIdCategoria){
        try {
            Categoria vCategoria = categoriaRepository
                .findById(pIdCategoria)
                .orElseThrow(
                    () -> new com.projeto.projeto.exception.NoSuchElementException("Categoria " + pIdCategoria + " não encontrada!")    
                );
            
            for(Obra obra : vCategoria.getObras()){
                obra.setCategoriaObra(null);
            }

            vCategoria.getObras().clear();

            categoriaRepository.delete(vCategoria);
            return ResponseEntity.ok().body(new MensagemDTO("OK", "OK"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("ERRO", e.getMessage())); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemDTO("ERRO", e.getMessage())); 
        }
    }
}