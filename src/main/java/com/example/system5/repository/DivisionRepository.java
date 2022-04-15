package com.example.system5.repository;

import com.example.system5.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Override
    List<Division> findAll();
}
