package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.ListReqDto;
import com.mangh.taskrit.dto.response.ListResDto;
import com.mangh.taskrit.mapper.poji.ListMapper;
import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.List;
import com.mangh.taskrit.service.poji.BoardRoleService;
import com.mangh.taskrit.service.poji.ListService;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "*")
public class ListController {

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private BoardRoleService boardRoleService;
    private ListService listService;
    private ListMapper listMapper;

    public ListController(JWTAuthorizationToken jwtAuthorizationToken, BoardRoleService boardRoleService,
                          ListService listService, ListMapper listMapper) {
        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.boardRoleService = boardRoleService;
        this.listService = listService;
        this.listMapper = listMapper;
    }


    @PostMapping("/create")
    public ResponseEntity<ListResDto> createList(@RequestHeader("Authorization") final String userToken,
                                                 @RequestBody ListReqDto listReqDto) throws Exception {
        this.log.info("[LIST][POST][CREATE]Request for creating new list");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // get board to insert list into
        final BoardInfo boardInfo = this.boardRoleService
                .getBoardRoleById(UUID.fromString(listReqDto.getBoardId()))
                .orElseThrow(() -> new Exception("Target board not found"));

        // map list and create it
        final List list = this.listService.create(this.listMapper.mapListReqDtoAndBoardToList(listReqDto,
                boardInfo.getBoard()));

        // return new list
        if (list.getListId() != null) {
            this.log.info("[LIST][POST][CREATE]List created successfully");
            return ResponseEntity.ok(this.listMapper.mapListToListResDto(list));
        }

        this.log.error("[LIST][POST][CREATE]Error creating list. Could not create list");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<java.util.List<ListResDto>> getBoardLists(
            @RequestHeader("Authorization") final String userToken,
            @PathVariable final String boardId) throws Exception {
        this.log.info("[LIST][GET][BY BOARD]Request for getting lists from board");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get board by id
        final BoardInfo boardInfo = this.boardRoleService
                .getBoardRoleById(UUID.fromString(boardId))
                .orElseThrow(() -> new Exception("Target board not found"));

        // Get lists by board
        final java.util.List<List> lists = this.listService.getListsByBoard(boardInfo.getBoard());

        // Parse lists and return response
        final java.util.List<ListResDto> listResDtos = this.listMapper.mapListsToListsResDtos(lists);

        this.log.info("[LIST][GET][BY BOARD]Lists obtained successfully");

        return ResponseEntity.ok(listResDtos);
    }

    @GetMapping("/details/{listId}")
    public ResponseEntity<ListResDto> getListById(@RequestHeader("Authorization") final String userToken,
                                                  @PathVariable final String listId) throws Exception {
        this.log.info("[LIST][GET][BY ID]Request for getting lists by id");

        // check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final List list = this.listService
                .getListById(UUID.fromString(listId)).orElseThrow(() -> new Exception("List not found"));

        return ResponseEntity.ok(this.listMapper.mapListToListResDto(list));
    }

    //DELETE delete list

}
