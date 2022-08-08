package com.example.qtest.repository;

import com.example.qtest.model.Attempttest;
import com.example.system5.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface AttemptestReporitory extends JpaRepository<Attempttest, Integer> {
    Page<Attempttest> findAllByUser(User user, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT test_id FROM q_attempttests")
    Set<Integer> findAllTestId();

    Page<Attempttest> findAll(Pageable pageable);

}
