package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListRepository extends JpaRepository<List, UUID> {

    @Override
    <S extends List> S save(S s);

    java.util.List<List> getListsByBoard(Board board);

    @Override
    Optional<List> findById(UUID uuid);
}
