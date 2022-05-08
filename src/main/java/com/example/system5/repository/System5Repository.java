package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RepositoryRestResource
public interface System5Repository extends JpaRepository<System5, Integer> {

    List<System5> findAllByUserId(Integer user_id);
    List<System5> findByUserId(int id);

    @Query(nativeQuery = true, value = "UPDATE system5 SET rated = 1 WHERE system5_id = :system5_id")
    @Transactional
    @Modifying
    void updateRated(int system5_id);
}
