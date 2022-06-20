package com.example.qtest.repository;

import com.example.qtest.model.GroupTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTestRepository extends JpaRepository<GroupTest, Integer> {
    List<GroupTest> findByOrderByGrouptestId();
}
