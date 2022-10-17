package com.example.kladr.repository;

import com.example.kladr.model.KladrAll;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KladrAllRepository extends JpaRepository<KladrAll, Integer> {
    List<KladrAll> findAllByNameContainingIgnoreCase(String name, PageRequest pageRequest);
}
