package com.projeto.projeto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projeto.Repository.CategoriaRepository;
import com.projeto.projeto.Repository.LocalRepository;
import com.projeto.projeto.Repository.ObraRepository;
import com.projeto.projeto.Repository.PersonagemRepository;
import com.projeto.projeto.exception.NoSuchElementException;
import com.projeto.projeto.model.Categoria;
import com.projeto.projeto.model.Local;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Obra;
import com.projeto.projeto.model.Personagem;

@Service
public class ObraService {
    
    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private LocalRepository localRepository;

    // GET ALL
    public List<Obra> findAll() {
        return obraRepository.findAll();
    }

    // GET
    public Obra findById(Integer pIdObra) {
        Obra vObra = obraRepository
            .findById(pIdObra)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Obra " + pIdObra + " não encontrada!")
            );
        
        return vObra;
    }

    // POST
    public Obra insObra(Obra pObra, Integer pIdCategoria) {
        Categoria vCategoria = categoriaRepository
            .findById(pIdCategoria)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Categoria " + pIdCategoria + " não encontrada!")
            );
        

        pObra.setIdObra(null);
        pObra.setCategoriaObra(vCategoria);
        return obraRepository.save(pObra);
    }

    // POST local <-> obra
    public Obra insLocalObra(Integer pIdObra, Integer pIdLocal){
        Obra vObra = obraRepository
            .findById(pIdObra)
            .orElseThrow();

        Local vLocal = localRepository
            .findById(pIdLocal)
            .orElseThrow();

        List<Local> vLocais = vObra.getLocais();
        vLocais.add(vLocal);
        vObra.setLocais(vLocais);

        try {
            vObra = obraRepository.save(vObra);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new com.projeto.projeto.exception.DataIntegrityViolationException( e.getMessage() +
                "Personagem "+vLocal.getNmLocal()+" já está inserido na obra "+vObra.getTitulo()+"!");
        }
        return vObra;
    }

    // POST personagem <-> obra
    public Obra insPersonagemObra(Integer pIdObra, Integer pIdPersonagem){
        Obra vObra = obraRepository
            .findById(pIdObra)
            .orElseThrow();

        Personagem vPersonagem = personagemRepository
            .findById(pIdPersonagem)
            .orElseThrow();

        List<Personagem> vPersonagens = vObra.getPersonagens();
        vPersonagens.add(vPersonagem);
        vObra.setPersonagens(vPersonagens);

        try {
            vObra = obraRepository.save(vObra);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new com.projeto.projeto.exception.DataIntegrityViolationException( e.getMessage() +
                "Personagem "+vPersonagem.getNome()+" já está inserido na obra "+vObra.getTitulo()+"!");
        }
        return vObra;
    }

    // PUT
    public Obra updObra(Integer pIdObra, Obra pObra){
        Obra pObraAtual = obraRepository
            .findById(pIdObra)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Obra " + pIdObra + " não encontrada!")
            );

        if(pObra.getTitulo() != null) {
            pObraAtual.setTitulo(pObra.getTitulo());
        }

        if(pObra.getDataLancamento() != null) {
            pObraAtual.setDataLancamento(pObra.getDataLancamento());
        }

        obraRepository.save(pObraAtual);
        return pObraAtual;
    }

    // DELETE
    public ResponseEntity<MensagemDTO> delObra(Integer pIdObra){
        try{
            obraRepository
                .findById(pIdObra)
                .orElseThrow(
                    () -> new com.projeto.projeto.exception.NoSuchElementException(
                    "Obra " + pIdObra + " não encontrada!")
                    );

            obraRepository.deleteById(pIdObra);
            return ResponseEntity.ok().body(new MensagemDTO("OK", "OK"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("ERRO", e.getMessage())); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemDTO("ERRO", e.getMessage())); 
        }
    }
}