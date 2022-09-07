package com.example.system5.repository;

import com.example.system5.model.CommEmpl;
import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommEmplRepository extends JpaRepository<CommEmpl, Integer>, JpaSpecificationExecutor<CommEmpl> {
    boolean existsByEmplUserAndCommUser(User emmplUser, User commUser);

    @Transactional
    @Modifying
    void deleteByEmplUserAndCommUser(User emmplUser, User commUser);



}
