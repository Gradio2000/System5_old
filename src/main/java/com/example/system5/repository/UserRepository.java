package com.example.system5.repository;

import com.example.system5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.name = :name")
    Optional<User> findByName(String name);

    @Query("select u from User u where u.login = :login")
    Optional<User> findByLogin(String login);

    User getUserByUserId(int id);

    boolean existsUserByLogin(String login);

    @Query(nativeQuery = true,
    value = "SELECT EXISTS(SELECT user_id FROM users WHERE position_id = :position_id)")
    boolean existsUserByPosition_id(Integer position_id);
}
