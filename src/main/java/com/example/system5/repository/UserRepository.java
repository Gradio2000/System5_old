package com.example.system5.repository;

import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    User getUserByUserId(int id);

    boolean existsUserByLogin(String login);

    @Query(nativeQuery = true,
            value = "SELECT EXISTS(SELECT user_id FROM position_user WHERE position_id = :position_id)")
    boolean existsUserByPosition_id(Integer position_id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "delete from position_user where position_id = :id")
    void deleteUser(int id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO commander_employee (commander_position_id, position_id) VALUES (:comm_id, :position_id)")
    void commEmpAdd(int comm_id, int position_id);
}
