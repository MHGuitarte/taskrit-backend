package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.TaskReqDto;
import com.mangh.taskrit.dto.response.TaskResDto;
import com.mangh.taskrit.mapper.poji.ListMapper;
import com.mangh.taskrit.mapper.poji.TaskMapper;
import com.mangh.taskrit.model.Task;
import com.mangh.taskrit.service.poji.ListService;
import com.mangh.taskrit.service.poji.TaskService;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
public class TaskController {

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private TaskService taskService;
    private TaskMapper taskMapper;
    private ListMapper listMapper;
    private ListService listService;

    public TaskController(JWTAuthorizationToken jwtAuthorizationToken, TaskService taskService, TaskMapper taskMapper,
                          ListMapper listMapper, ListService listService) {
        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.listMapper = listMapper;
        this.listService = listService;
    }

    @GetMapping("/create")
    public ResponseEntity<TaskResDto> createList(@RequestHeader("Authorization") final String userToken,
                                                 @RequestBody TaskReqDto taskReqDto) throws Exception {
        this.log.info("[TASK][POST][CREATE]Request for creating new task");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // get target list
        final com.mangh.taskrit.model.List list = this.listService
                .getListById(UUID.fromString(taskReqDto.getListId()))
                .orElseThrow(() -> new Exception("Target List not found"));

        final Task task = this.taskService.create(this.taskMapper.mapTaskReqDtoToTask(taskReqDto, list));

        if (task.getTaskId() != null) {
            this.log.info("[TASK][POST][CREATE]Task created successfully");
            return ResponseEntity.ok(this.taskMapper.mapTaskToTaskResDto(task));
        }

        this.log.error("[TASK][POST][CREATE]Error creating task. Could not create task");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/details/{taskId}")
    ResponseEntity<TaskResDto> getTaskById(@RequestHeader("Authorization") final String userToken,
                                           @PathVariable final String taskId) throws Exception {
        this.log.info("[TASK][GET][BY ID]Request for getting task by id");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Task task = this.taskService
                .findById(UUID.fromString(taskId)).orElseThrow(() -> new Exception("Task not found"));

        this.log.info("[TASK][GET][BY ID]Task retrieved successfully");
        return ResponseEntity.ok(this.taskMapper.mapTaskToTaskResDto(task));
    }

    @GetMapping("/{listId}")
    ResponseEntity<List<TaskResDto>> getTasksByList(@RequestHeader("Authorization") final String userToken,
                                                    @PathVariable final String listId) throws Exception {
        this.log.info("[TASK][GET][FROM LIST]Request for getting tasks from list");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final com.mangh.taskrit.model.List list = this.listService
                .getListById(UUID.fromString(listId)).orElseThrow(() -> new Exception("List not found"));

        final List<Task> tasks = this.taskService.getTasksByList(list);

        final List<TaskResDto> taskResDtos = this.taskMapper.mapTaskListToTaskResDtoList(tasks);

        this.log.info("[TASK][GET][FROM LIST]Tasks obtained successfully");

        return ResponseEntity.ok(taskResDtos);
    }

    @PutMapping("/move/{taskId}/{listId}")
    ResponseEntity<Boolean> changeTaskFromList(@RequestHeader("Authorization") final String userToken,
                                               @PathVariable final String taskId,
                                               @PathVariable final String listId) throws Exception {
        this.log.info("[TASK][PUT][CHANGE LIST]Request for changing task from list");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final com.mangh.taskrit.model.List list = this.listService
                .getListById(UUID.fromString(listId))
                .orElseThrow(() -> new Exception("Target list does not exist"));

        final Task task = this.taskService
                .findById(UUID.fromString(taskId))
                .orElseThrow(() -> new Exception("Target task not found"));

        if (task.getList().equals(list)) {
            this.log.error("[TASK][PUT][CHANGE LIST]Cannot change task to same list");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Boolean.FALSE);
        }

        this.taskService.changeTaskFromList(list, UUID.fromString(taskId));

        this.log.info("[TASK][PUT][CHANGE LIST]Task list changed successfully");

        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/pending/{taskId}")
    ResponseEntity<Boolean> setTaskPending(@RequestHeader("Authorization") final String userToken,
                                           @PathVariable final String taskId,
                                           @RequestBody Double pending) throws Exception {
        this.log.info("[TASK][PUT][SET PENDING]Request for changing task's pending time");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (pending.isNaN() || pending < 0) {
            this.log.error("[TASK][PUT][SET PENDING]Cannot set pending time below 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Boolean.FALSE);
        }

        final Task task = this.taskService
                .findById(UUID.fromString(taskId)).orElseThrow(() -> new Exception("Task not found"));

        if (task.getEstimate() < pending) {
            this.log.error("[TASK][PUT][SET PENDING]Cannot set greater pending time than estimate time");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Boolean.FALSE);
        }

        this.taskService.changeRemainingTime(pending, UUID.fromString(taskId));

        this.log.info("[TASK][PUT][SET PENDING] Pending time changed successfully");

        return ResponseEntity.ok(Boolean.TRUE);
    }

    // PUT set remaining

}
