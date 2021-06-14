package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    <S extends User> S save(S s);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
