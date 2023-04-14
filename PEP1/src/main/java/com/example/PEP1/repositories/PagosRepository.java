package com.example.PEP1.repositories;

import com.example.PEP1.entities.PagosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends CrudRepository<PagosEntity, Long> {
}
