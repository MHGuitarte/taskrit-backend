package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.Task;
import com.mangh.taskrit.repository.TaskRepository;
import com.mangh.taskrit.service.poji.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task task) {
        return this.taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(UUID taskId) {
        return this.taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getTasksByList(com.mangh.taskrit.model.List list) {
        return this.taskRepository.findAllByList(list);
    }
}
