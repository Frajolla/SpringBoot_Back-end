package com.projeto.projeto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.projeto.model.Obra;

public interface ObraRepository extends JpaRepository<Obra,Integer>{
     @Query(value = "SELECT id_obra, data_lancamento, titulo FROM obra WHERE id_local = :pIdLocal ORDER BY nm_local", nativeQuery = true)
    List<Obra> searchByLocal(@Param("pIdLocal") Integer pIdLocal);
}