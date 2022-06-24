package com.example.qtest.repository;

import com.example.qtest.model.ResultTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultTestReposytory extends JpaRepository<ResultTest, Integer> {
}
