package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRoleRepository extends JpaRepository<BoardInfo, UUID> {

    @Override
    <S extends BoardInfo> S save(S s);

    List<BoardInfo> findAllByUser(User user);

    @Override
    Optional<BoardInfo> findById(UUID uuid);
}
