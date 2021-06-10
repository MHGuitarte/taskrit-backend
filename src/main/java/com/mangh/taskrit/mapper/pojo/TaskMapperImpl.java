package com.mangh.taskrit.mapper.pojo;

import com.mangh.taskrit.dto.request.TaskReqDto;
import com.mangh.taskrit.dto.response.TaskResDto;
import com.mangh.taskrit.mapper.poji.TaskMapper;
import com.mangh.taskrit.model.List;
import com.mangh.taskrit.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task mapTaskReqDtoToTask(TaskReqDto taskReqDto, List list) {
        final Task task = new Task();

        task.setTitle(taskReqDto.getTitle());
        task.setDescription(taskReqDto.getDescription());
        task.setEffort(taskReqDto.getEffort());
        task.setEstimate(taskReqDto.getEstimate());
        task.setPending(taskReqDto.getPending());

        return task;
    }

    @Override
    public TaskResDto mapTaskToTaskResDto(Task task) {
        return TaskResDto.builder()
                .id(task.getTaskId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .responsibleId(task.getResponsible().getUserId().toString())
                .effort(task.getEffort())
                .estimate(task.getEstimate())
                .pending(task.getPending())
                .listId(task.getList().getListId().toString())
                .build();
    }

    @Override
    public java.util.List<TaskResDto> mapTaskListToTaskResDtoList(java.util.List<Task> tasks) {
        return tasks.stream().map(this::mapTaskToTaskResDto).collect(Collectors.toList());
    }
}
