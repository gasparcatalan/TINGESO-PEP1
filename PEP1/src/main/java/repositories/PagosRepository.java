package repositories;

import entities.PagosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends CrudRepository<PagosEntity, Long> {
}
