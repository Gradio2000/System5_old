package com.example.qtest.repository;

import com.example.qtest.model.QuestionsForAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionForAttemptRepository extends JpaRepository<QuestionsForAttempt, Integer> {
    List<QuestionsForAttempt> findAllByAttemptId(Integer attemptId);
}
