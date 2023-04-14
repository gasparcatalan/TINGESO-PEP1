package com.example.PEP1.repositories;

import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Long> {

    public ProveedorEntity findByNombre(String nombre);

    @Query("select p from ProveedorEntity  p where p.nombre = :nombre")
    ProveedorEntity findByNameCustomQuery(@Param("nombre") String nombre);

    @Query("select p from ProveedorEntity p where p.codigo =:codigo")
    ProveedorEntity findProveedorByCodigo(@Param("codigo") Long codigo);
    /*
    *  proveedorRepository.finByNombre("")
    *
    *
    * */


}
