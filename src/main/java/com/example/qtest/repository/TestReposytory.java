package com.example.qtest.repository;

import com.example.qtest.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TestReposytory extends JpaRepository<Test, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM q_tests WHERE deleted = false AND group_id = :groupId ORDER BY test_id")
    List<Test> findAllDeletedTestsByGroupIdOrderByTestId(Integer groupId);

    List<Test> findAllByGroupIdOrderByTestId(Integer groupId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE q_tests SET deleted = true WHERE test_id IN :ids")
    void makeTestsDeletedTrue(Integer[] ids);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE q_tests SET deleted = false WHERE test_id = :testId")
    void undeleteTest(Integer testId);
}
