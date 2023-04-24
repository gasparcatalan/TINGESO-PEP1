package com.example.PEP1.repositories;

import com.example.PEP1.entities.AcopioEntity;
import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface AcopioRepository extends CrudRepository<AcopioEntity,Long> {
    @Query("select a from AcopioEntity a where a.codigo_proveedor = :codigo" +
            " and a.fecha = :fecha")
    ArrayList<AcopioEntity> findAllByCodigo_proveedorAAndFecha(@Param("codigo") Long codigo
                                                              ,@Param("fecha")Date fecha);

    @Query("select  sum (a.kls_leche) from AcopioEntity a where a.codigo_proveedor = :codigo" +
            " and a.fecha = :fecha")
        Double totalLeche(@Param("codigo") Long codigo
                      ,@Param("fecha")Date fecha);


    @Query("select count(a) from AcopioEntity a where a.codigo_proveedor =:codigo "+
           "and a.turno =:turno")
    Integer countTurno(@Param("codigo") Long codigo,
                              @Param("turno") String turno);
}
