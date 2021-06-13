package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    @Override
    @Transactional
    <S extends Board> S save(S s);

    @Override
    @Transactional
    Optional<Board> findById(UUID uuid);
}
