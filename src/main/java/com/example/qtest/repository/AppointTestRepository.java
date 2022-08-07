package com.example.qtest.repository;

import com.example.qtest.model.AppointTest;
import com.example.qtest.model.Test;
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

    @Query(nativeQuery = true,
    value = "SELECT * FROM q_appoint_tests WHERE test_id = :testId AND user_id = :userId")
    AppointTest getAppointTest(Integer testId, Integer userId);

    AppointTest findByUserAndTestAndFinished(User user, Test test, Boolean finished);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "DELETE FROM q_appoint_tests WHERE user_id = :userId AND test_id = :testId")
    void deleteByUserAndTest(Integer userId, Integer testId);

}
