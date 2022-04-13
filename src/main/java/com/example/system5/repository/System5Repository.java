package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface System5Repository extends JpaRepository<System5, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT system5_id, user_id, month, res1, res2, res3, res4, res5" +
                    " FROM system5 WHERE system5_id = :id AND user_id =:user_id")
    System5 getById(Integer id, Integer user_id);


    @Query(nativeQuery = true,
            value = "SELECT system5_id, user_id, month, res1, res2, res3, res4, res5" +
                    " FROM system5 WHERE user_id = :user_id")
    List<System5> findAll(Integer user_id);

}
