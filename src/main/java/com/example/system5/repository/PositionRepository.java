package com.example.system5.repository;

import com.example.system5.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    @Override
    Position getById(Integer integer);
}
