package com.example.qtest.repository;

import com.example.qtest.model.ResultTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultTestRepository extends JpaRepository<ResultTest, Integer> {
    List<ResultTest> findAllByAttemptIdOrderById(Integer attemptId);
}
