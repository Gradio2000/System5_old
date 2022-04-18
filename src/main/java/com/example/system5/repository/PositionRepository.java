package com.example.system5.repository;

import com.example.system5.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query(value = "select p from Position p where p.position_id = :position_id")
    Position getByPosition_id(int position_id);
}
