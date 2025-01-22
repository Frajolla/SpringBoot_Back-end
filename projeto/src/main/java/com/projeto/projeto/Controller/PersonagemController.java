package com.projeto.projeto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.projeto.Service.PersonagemService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.net.URI;

import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Personagem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController // controller do rest
@RequestMapping(value = "/personagem")
public class PersonagemController {

    @Autowired
    private PersonagemService personagemService;

    @GetMapping()
    public ResponseEntity<List<Personagem>> findAll() {
        return ResponseEntity.ok().body(personagemService.findAll());
    }

    @GetMapping(value = "/{pIdPersonagem}")
    public ResponseEntity<Personagem> findById(@PathVariable Integer pIdPersonagem) {
        return ResponseEntity.ok().body(personagemService.findById(pIdPersonagem));
    }

    @PostMapping
    public ResponseEntity<Personagem> inserePersonagem(@RequestBody Personagem personagem) {
        Personagem vNovPersonagem = personagemService.inserePersonagem(personagem);

        URI vUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/personagem/{pIdPersonagem}")
                .buildAndExpand(vNovPersonagem.getIdPersonagem()).toUri();

        return ResponseEntity.created(vUri).body(vNovPersonagem);
    }

    @PostMapping("/{pIdPersonagem}/ator/{pIdAtor}")
    public ResponseEntity<MensagemDTO> insAtorPersonagem(@PathVariable Integer pIdPersonagem, @PathVariable Integer pIdAtor) {
        personagemService.insAtorPersonagem(pIdPersonagem, pIdAtor);
        
        return ResponseEntity.ok().body(new MensagemDTO("OK", "Ator "+pIdAtor+" inserido no personagem " + pIdPersonagem));
    }
    
    @PutMapping("/{pIdPersonagem}")
    public ResponseEntity<Personagem> updatePersonagem(@PathVariable Integer pIdPersonagem,
            @RequestBody Personagem pPersonagem) {

        Personagem vNovPersonagem = personagemService.updPersonagem(pIdPersonagem, pPersonagem);

        return ResponseEntity.ok().body(vNovPersonagem);
    }

    @DeleteMapping("/{pIdPersonagem}")
    public ResponseEntity<MensagemDTO> delPersonagem(@PathVariable Integer pIdPersonagem){
        return personagemService.delPersonagem(pIdPersonagem);
    }

}