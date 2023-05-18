package com.example.PEP1.repositories;

import com.example.PEP1.entities.LaboratorioEntity;
import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface LaboratorioRepository extends CrudRepository<LaboratorioEntity, Long> {

    @Query
            ("select l from LaboratorioEntity l where l.codigoProveedor = :codigo")
            LaboratorioEntity findByCodigo(@Param("codigo") Long Codigo);

    @Query
            ("select p from ProveedorEntity  p where p.nombre = :nombre")
    ProveedorEntity findByNameCustomQuery(@Param("nombre") String nombre);

}
