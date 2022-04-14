package com.example.system5.repository;

import com.example.system5.model.Shtat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShtatRepository extends JpaRepository<Shtat, Integer> {
    @Override
    public List<Shtat> findAll();
}
