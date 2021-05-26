package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    @Override
    <S extends Board> S save(S s);

    @Override
    Optional<Board> findById(UUID uuid);
}
