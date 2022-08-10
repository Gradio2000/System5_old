package com.example.qtest.repository;

import com.example.qtest.model.FalseUsersAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FalseUsersAnswerRepository extends JpaRepository<FalseUsersAnswer, Integer> {
}