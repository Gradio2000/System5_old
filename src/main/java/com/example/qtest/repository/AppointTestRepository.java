package com.example.qtest.repository;

import com.example.qtest.model.AppointTest;
import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointTestRepository extends JpaRepository<AppointTest, Integer> {
    List<AppointTest> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "UPDATE q_appoint_tests SET finished = true WHERE id_appoint_test = :appointTestId")
    void setAppointTrue(Integer appointTestId);

    List<AppointTest> findAllByFinishedAndEko(Boolean finished, Boolean eko);
}
