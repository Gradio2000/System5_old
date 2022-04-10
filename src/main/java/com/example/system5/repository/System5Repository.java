package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface System5Repository extends JpaRepository<System5, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT system5_id, month_name, res1, res2, res3, res4, res5 FROM system5 JOIN months m on system5.month_id = m.month_id  WHERE system5_id = :id")
    @Override
    System5 getById(Integer id);

    @Override
    @Query(nativeQuery = true,
            value = "SELECT system5_id, month_name, res1, res2, res3, res4, res5 FROM system5 JOIN months m on system5.month_id = m.month_id")
    List<System5> findAll();
}
