package repositories;

import entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Long> {

    public ProveedorEntity findByNombre(String nombre);

    @Query("select p from ProveedorEntity  p where p.nombre = :nombre")
    ProveedorEntity findByNameCustomQuery(@Param("nombre") String nombre);


}
