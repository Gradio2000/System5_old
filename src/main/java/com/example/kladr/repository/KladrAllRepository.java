package com.example.kladr.repository;

import com.example.kladr.model.KladrAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KladrAllRepository extends JpaRepository<KladrAll, Integer> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM kladr_all WHERE name ~ :value1 LIMIT 10")
    List<KladrAll> selectAll(String value1);

    @Query(nativeQuery = true,
            value = "SELECT * FROM kladr_all WHERE name ~ :value1 AND name ~ :value2 LIMIT 10")
    List<KladrAll> selectAll(String value1, String value2);

    @Query(nativeQuery = true,
            value = "SELECT * FROM kladr_all WHERE name ~ :value1 AND name ~ :value2 AND name ~ :value3 LIMIT 10")
    List<KladrAll> selectAll(String value1, String value2, String value3);
}
