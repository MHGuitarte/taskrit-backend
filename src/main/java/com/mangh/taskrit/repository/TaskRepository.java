package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Override
    <S extends Task> S save(S s);

    @Override
    Optional<Task> findById(UUID uuid);

    List<Task> findAllByList(com.mangh.taskrit.model.List list);

    @Modifying
    @Query("update Task t set t.list = :list where t.taskId = :taskId")
    void changeTaskList(com.mangh.taskrit.model.List list, UUID taskId);

    @Modifying
    @Query("update Task t set t.pending = :pending where t.taskId = :taskId")
    void changeRemainingTime(Double pending, UUID taskId);
}
