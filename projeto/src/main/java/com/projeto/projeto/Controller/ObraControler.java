package com.projeto.projeto.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.projeto.Service.ObraService;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Obra;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController // controller do rest
@RequestMapping(value = "/obra")
public class ObraControler {

    @Autowired
    private ObraService obraService;

    @GetMapping()
    public ResponseEntity<List<Obra>> findAll() {
        return ResponseEntity.ok().body(obraService.findAll());
    }

    @GetMapping("/{pIdObra}")
    public ResponseEntity<Obra> findById(@PathVariable Integer pIdObra) {
        return ResponseEntity.ok().body(obraService.findById(pIdObra));
    }
    
    @PostMapping(value = "/{pIdCategoria}")
    public ResponseEntity<Obra> insObra(@PathVariable Integer pIdCategoria,@RequestBody Obra pObra) {
        Obra vNovObra = obraService.insObra(pObra, pIdCategoria);

        URI vUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/obra/{pIdObra}")
                .buildAndExpand(vNovObra.getIdObra()).toUri();
        return ResponseEntity.created(vUri).body(vNovObra);
    }

    @PostMapping("/{pIdObra}/personagem/{pIdPersonagem}")
    public ResponseEntity<MensagemDTO> insPersonagemObra(
        @PathVariable Integer pIdObra, @PathVariable Integer pIdPersonagem) {
        obraService.insPersonagemObra(pIdObra, pIdPersonagem);
        
        return ResponseEntity.ok().body(new MensagemDTO(
        "OK",
        "Personagem " + pIdPersonagem + " inserido na obra" + pIdObra
        ));
    }

    @PostMapping("/{pIdObra}/local/{pIdLocal}")
    public ResponseEntity<MensagemDTO> insLocalObra(
        @PathVariable Integer pIdObra, @PathVariable Integer pIdLocal) {
        obraService.insLocalObra(pIdObra, pIdLocal);
        
        return ResponseEntity.ok().body(new MensagemDTO(
        "OK",
        "Local " + pIdLocal + " inserido na obra " + pIdObra
        ));
    }

    @PutMapping("/{pIdObra}")
    public ResponseEntity<Obra> updObra(
        @PathVariable Integer pIdObra, @RequestBody Obra pObra) {
        Obra vNovObra = obraService.updObra(pIdObra, pObra);
        
        return ResponseEntity.ok().body(vNovObra);
    }
    
    @DeleteMapping(value = "/{pIdObra}")
    public ResponseEntity<MensagemDTO> delObra(@PathVariable Integer pIdObra){
            return obraService.delObra(pIdObra);
    }
}