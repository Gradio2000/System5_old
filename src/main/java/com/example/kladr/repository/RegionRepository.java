package com.example.kladr.repository;


import com.example.kladr.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query("SELECT r FROM Region r WHERE r.name LIKE %:val%")
    List<Region> getRegion(@Param("val") String val);
}
