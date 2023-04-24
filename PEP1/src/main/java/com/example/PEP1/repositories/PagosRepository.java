package com.example.PEP1.repositories;

import com.example.PEP1.entities.PagosEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PagosRepository extends CrudRepository<PagosEntity, Long> {


    @Query("SELECT p FROM PagosEntity p WHERE p.codigo_proveedor = :codigo_proveedor AND p.fecha < :fecha ORDER BY p.fecha DESC")
    PagosEntity findAnteriorPago(@Param("codigo_proveedor") Long codigo_proveedor, @Param("fecha") Date fecha);

}
