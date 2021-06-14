package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRoleRepository extends JpaRepository<BoardInfo, UUID> {

    @Override
    @Transactional
    <S extends BoardInfo> S save(S s);

    @Transactional
    List<BoardInfo> findAllByUser(User user);

    @Override
    @Transactional
    Optional<BoardInfo> findById(UUID uuid);
}
