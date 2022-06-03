package com.example.system5.repository;

import com.example.system5.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PositionRepository extends JpaRepository<Position, Integer> {
    List<Position> findAllByDivisionId(int id);
}
