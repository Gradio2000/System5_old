package com.example.system5.repository;

import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    boolean existsUserByLogin(String login);

    @Query(nativeQuery = true,
            value = "SELECT EXISTS(SELECT user_id FROM sys_position_user WHERE position_id = :position_id)")
    boolean existsUserByPosition_id(Integer position_id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "delete from sys_position_user where position_id = :id")
    void deleteUser(int id);


    @Query(nativeQuery = true,
            value = "SELECT empl_id FROM sys_com_empl WHERE comm_id = :commId")
    List<Integer> getEmplUserIdList(int commId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "DELETE FROM sys_com_empl WHERE empl_id = :emplId AND comm_id = :commId")
    void deleteCommEmpl(int emplId, int commId);
}
