package com.projeto.projeto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projeto.Repository.LocalRepository;
import com.projeto.projeto.exception.NoSuchElementException;
import com.projeto.projeto.model.Local;
import com.projeto.projeto.model.MensagemDTO;
import com.projeto.projeto.model.Obra;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;


    // GET ALL
    public List<Local> findAll() {
        return localRepository.findAll();
    }

    // GET
    public Local findById(Integer pIdLocal) {
        Local vLocal = localRepository
            .findById(pIdLocal)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Local " + pIdLocal + " não encontrado!")
            );

        return vLocal;
    }

    // POST
    public Local insLocal(Local pLocal) {
        pLocal.setIdLocal(null);
        return localRepository.save(pLocal);
    }

    // PUT
    public Local updLocal(Integer pIdLocal, Local pLocal){
        Local vLocal = localRepository
            .findById(pIdLocal)
            .orElseThrow(
                () -> new com.projeto.projeto.exception.NoSuchElementException("Local " + pIdLocal + " não encontrado!")
            );

        if (pLocal.getNmLocal() != null){
            vLocal.setNmLocal(pLocal.getNmLocal());
        }
        if (pLocal.getHistoriaLocal() != null){
            vLocal.setHistoriaLocal(pLocal.getHistoriaLocal());
        }

        localRepository.save(vLocal);
        return vLocal;
    }

    // DELETE
    public ResponseEntity<MensagemDTO> delLocal(Integer pIdLocal){
        try {
            Local vLocal = localRepository
                .findById(pIdLocal)
                .orElseThrow(() -> new com.projeto.projeto.exception.NoSuchElementException(
                    "Local " + pIdLocal + " não encontrado!"));

            for(Obra obra : vLocal.getObras()){
                obra.getLocais().remove(vLocal);
            }

            vLocal.getObras().clear();

            localRepository.delete(vLocal);
            return ResponseEntity.ok().body(new MensagemDTO("OK", "OK"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("ERRO", e.getMessage())); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemDTO("ERRO", e.getMessage())); 
        }
    }
}