package com.example.qtest.repository;

import com.example.qtest.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestReposytory extends JpaRepository<Test, Integer> {
//    List<Test> findAllByGroupId(Integer groupId);
    List<Test> findAllByGroupTestGrouptestId(Integer id);
}
