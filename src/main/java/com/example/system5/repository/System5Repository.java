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

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE system5 SET resempl1 = :resempl1, resempl2 = :resempl2, resempl3 = :resempl3, " +
            "resempl4 = :resempl4, resempl5 = :resempl5, rated = :rated " +
            "WHERE user_id = :user_id AND month = :month")
    void updateSystem5(String resempl1, String resempl2, String resempl3,
                       String resempl4, String resempl5, int rated, int user_id,
                       String month);
}
