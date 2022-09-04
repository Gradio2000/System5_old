package com.example.qtest.repository;

import com.example.qtest.model.AppointTestAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface AppointTestAmountRepository extends JpaRepository<AppointTestAmount, Integer> {

    @Transactional
    @Modifying
    void deleteAllByAppointId(Integer appointId);
}
