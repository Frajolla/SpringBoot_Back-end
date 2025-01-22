package com.projeto.projeto.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.projeto.Service.LocalService;
import com.projeto.projeto.model.Local;
import com.projeto.projeto.model.MensagemDTO;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(value = "/local")
public class LocalControler {

    @Autowired
    private LocalService localService;

    @GetMapping()
    public ResponseEntity<List<Local>> findAll() {
        return ResponseEntity.ok().body(localService.findAll());
    }

    @GetMapping("/{pIdLocal}")
    public ResponseEntity<Local> findById(@PathVariable Integer pIdLocal) {
        return ResponseEntity.ok().body(localService.findById(pIdLocal));
    }
    
    @PostMapping
    public ResponseEntity<Local> insLocal(@RequestBody Local pLocal) {
        Local vNovLocal = localService.insLocal(pLocal);

        URI vUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/local/{pIdLocal}")
                .buildAndExpand(vNovLocal.getIdLocal()).toUri();

        return ResponseEntity.created(vUri).body(vNovLocal);
    }

    @PutMapping("/{pIdLocal}")
    public ResponseEntity<Local> updLocal(@PathVariable Integer pIdLocal, @RequestBody Local pLocal) {
        Local vNovLocal = localService.updLocal(pIdLocal, pLocal);
        return ResponseEntity.ok().body(vNovLocal);
    }

    @DeleteMapping("/{pIdLocal}")
    public ResponseEntity<MensagemDTO> delLocal(@PathVariable Integer pIdLocal){
        return localService.delLocal(pIdLocal);
    }
    
}