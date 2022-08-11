package com.example.qtest.repository;

import com.example.qtest.model.FalseUsersAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FalseUsersAnswerRepository extends JpaRepository<FalseUsersAnswer, Integer> {

    List<FalseUsersAnswer> findAllByAttemptId(Integer attemptId);
}