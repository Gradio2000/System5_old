package com.example.search.repository;

import com.example.search.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM pindx17 WHERE opsname LIKE :val")
    List<Search> getAdress(String val);
}
