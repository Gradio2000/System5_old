package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNullApi;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface System5Repository extends JpaRepository<System5, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM system5 WHERE system5_id = :id")
    @Override
    System5 getById(Integer id);

}
