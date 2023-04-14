package com.example.PEP1.repositories;

import com.example.PEP1.entities.LaboratorioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LaboratorioRepository extends CrudRepository<LaboratorioEntity, Long> {
}
