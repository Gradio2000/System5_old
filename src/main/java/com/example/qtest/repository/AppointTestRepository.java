package com.example.qtest.repository;

import com.example.qtest.model.AppointTest;
import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointTestRepository extends JpaRepository<AppointTest, Integer> {
    List<AppointTest> findAllByUser(User user);
}
