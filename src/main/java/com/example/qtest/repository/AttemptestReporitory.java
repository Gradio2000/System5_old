package com.example.qtest.repository;

import com.example.qtest.model.Attempttest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttemptestReporitory extends JpaRepository<Attempttest, Integer> {
    Page<Attempttest> findAllByUserId(Integer userId, Pageable pageable);
}
