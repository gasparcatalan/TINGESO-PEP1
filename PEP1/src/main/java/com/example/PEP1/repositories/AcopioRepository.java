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
    @Query("select a from AcopioEntity a where a.codigo_proveedor = :codigo")
    ArrayList<AcopioEntity> findAllByCodigo_proveedor(@Param("codigo") Long codigo);

    @Query("select  sum (a.kls_leche) from AcopioEntity a where a.codigo_proveedor = :codigo")
        Double totalLeche(@Param("codigo") Long codigo);


    @Query("select count(a) from AcopioEntity a where a.codigo_proveedor =:codigo "+
           "and a.turno =:turno")
    Integer countTurno(@Param("codigo") Long codigo,
                              @Param("turno") String turno);
}
