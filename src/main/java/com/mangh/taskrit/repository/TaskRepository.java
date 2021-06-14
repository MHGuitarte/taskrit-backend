package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Task;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Override
    @Transactional
    <S extends Task> S save(S s);

    @Override
    @Transactional
    Optional<Task> findById(UUID uuid);

    @Transactional
    List<Task> findAllByList(com.mangh.taskrit.model.List list);
}
