package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.TaskReqDto;
import com.mangh.taskrit.dto.response.TaskResDto;
import com.mangh.taskrit.mapper.poji.TaskMapper;
import com.mangh.taskrit.model.Task;
import com.mangh.taskrit.service.poji.ListService;
import com.mangh.taskrit.service.poji.TaskService;
import com.mangh.taskrit.util.pojo.Logger;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
@Tag(name = "Tasks", description = "Handle task CRUD and extra executions")
public class TaskController {

    private static final String TOKEN_ERROR_MESSAGE = "Unauthorized webtoken provided";

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private TaskService taskService;
    private TaskMapper taskMapper;
    private ListService listService;

    public TaskController(JWTAuthorizationToken jwtAuthorizationToken, TaskService taskService, TaskMapper taskMapper,
                          ListService listService) {
        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.listService = listService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResDto> createList(@RequestHeader("Authorization") final String userToken,
                                                 @RequestBody TaskReqDto taskReqDto) throws Exception {
        this.log.info("[TASK][POST][CREATE]Request for creating new task");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error(TOKEN_ERROR_MESSAGE.toUpperCase());
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
    public ResponseEntity<TaskResDto> getTaskById(@RequestHeader("Authorization") final String userToken,
                                           @PathVariable final String taskId) throws Exception {
        this.log.info("[TASK][GET][BY ID]Request for getting task by id");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error(TOKEN_ERROR_MESSAGE.toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Task task = this.taskService
                .findById(UUID.fromString(taskId)).orElseThrow(() -> new Exception("Task not found"));

        this.log.info("[TASK][GET][BY ID]Task retrieved successfully");
        return ResponseEntity.ok(this.taskMapper.mapTaskToTaskResDto(task));
    }

    @GetMapping("/{listId}")
    public ResponseEntity<List<TaskResDto>> getTasksByList(@RequestHeader("Authorization") final String userToken,
                                                    @PathVariable final String listId) throws Exception {
        this.log.info("[TASK][GET][FROM LIST]Request for getting tasks from list");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error(TOKEN_ERROR_MESSAGE.toUpperCase());
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
    public ResponseEntity<Boolean> changeTaskFromList(@RequestHeader("Authorization") final String userToken,
                                               @PathVariable final String taskId,
                                               @PathVariable final String listId) throws Exception {
        this.log.info("[TASK][PUT][CHANGE LIST]Request for changing task from list");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error(TOKEN_ERROR_MESSAGE.toUpperCase());
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

        task.setList(list);

        this.taskService.create(task); // create function does upsert logic

        this.log.info("[TASK][PUT][CHANGE LIST]Task list changed successfully");

        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/pending/{taskId}")
    public ResponseEntity<Boolean> setTaskPending(@RequestHeader("Authorization") final String userToken,
                                           @PathVariable final String taskId,
                                           @RequestBody Double pending) throws Exception {
        this.log.info("[TASK][PUT][SET PENDING]Request for changing task's pending time");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error(TOKEN_ERROR_MESSAGE.toUpperCase());
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

        task.setPending(pending);

        this.taskService.create(task); // create function does upsert logic

        this.log.info("[TASK][PUT][SET PENDING] Pending time changed successfully");

        return ResponseEntity.ok(Boolean.TRUE);
    }

}
