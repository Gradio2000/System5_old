package com.example.kladr.repository;

import com.example.kladr.model.Kladr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KladrRepository extends JpaRepository<Kladr, Integer> {
    List<Kladr>findAllByNameContainingIgnoreCase(String name);
}
