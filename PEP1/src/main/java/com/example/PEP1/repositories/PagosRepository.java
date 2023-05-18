package com.example.PEP1.repositories;

import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface PagosRepository extends CrudRepository<PagosEntity, Long> {

    @Query("select p from PagosEntity p where p.codigoProveedor =:codigo")
    ArrayList<PagosEntity> findAllByCodigo_proveedor(Long codigo);

    @Query("select p from PagosEntity p where p.idPago =:id_pago")
    PagosEntity findporId(Long id_pago);

    @Query("SELECT p FROM PagosEntity p WHERE p.codigoProveedor = :codigo_proveedor AND p.fecha < :fecha ORDER BY p.fecha DESC")
    PagosEntity findAnteriorPago(@Param("codigo_proveedor") Long codigo_proveedor, @Param("fecha") Date fecha);

}
