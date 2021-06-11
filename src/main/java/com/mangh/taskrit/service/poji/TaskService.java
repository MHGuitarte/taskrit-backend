package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.List;
import com.mangh.taskrit.model.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    Task create (final Task task);

    Optional<Task> findById(final UUID taskId);

    java.util.List<Task> getTasksByList(final List list);

    void changeTaskFromList(final List list, UUID taskId);

    void changeRemainingTime(final Double pending, UUID taskId);
}
