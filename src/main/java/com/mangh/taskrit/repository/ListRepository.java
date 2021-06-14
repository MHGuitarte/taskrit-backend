package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListRepository extends JpaRepository<List, UUID> {

    @Override
    @Transactional
    <S extends List> S save(S s);

    @Transactional
    java.util.List<List> getListsByBoard(Board board);

    @Override
    @Transactional
    Optional<List> findById(UUID uuid);
}
