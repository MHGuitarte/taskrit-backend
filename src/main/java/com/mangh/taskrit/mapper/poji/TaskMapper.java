package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.TaskReqDto;
import com.mangh.taskrit.dto.response.TaskResDto;
import com.mangh.taskrit.model.Task;

import java.util.List;

public interface TaskMapper {

    Task mapTaskReqDtoToTask (final TaskReqDto taskReqDto, com.mangh.taskrit.model.List list);

    TaskResDto mapTaskToTaskResDto(final Task task);

    List<TaskResDto> mapTaskListToTaskResDtoList(final List<Task> tasks);




}
