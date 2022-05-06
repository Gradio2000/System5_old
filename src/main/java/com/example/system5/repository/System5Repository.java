package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface System5Repository extends JpaRepository<System5, Integer> {

    List<System5> findAllByUserId(Integer user_id);

    List<System5> findByUserId(int id);

    @Query(nativeQuery = true,
    value = "UPDATE system5 SET resempl1 = :resempl1 WHERE user_id = :user_id AND month = :month")
    void updateSistem5 (String resempl1, String month);

}
