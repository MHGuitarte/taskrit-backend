package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.dto.response.BoardResDto;
import com.mangh.taskrit.mapper.poji.BoardMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.BoardRoleService;
import com.mangh.taskrit.service.poji.BoardService;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "*")
public class BoardController {

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private BoardRoleService boardRoleService;
    private BoardService boardService;
    private UserService userService;
    private BoardMapper boardMapper;

    public BoardController(JWTAuthorizationToken jwtAuthorizationToken, BoardRoleService boardRoleService,
                           BoardService boardService, UserService userService, BoardMapper boardMapper) {

        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.boardRoleService = boardRoleService;
        this.boardService = boardService;
        this.userService = userService;
        this.boardMapper = boardMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<BoardResDto> createBoard(@RequestHeader("Authorization") final String userToken,
                                                   @RequestBody BoardReqDto boardReqDto) {
        this.log.info("[BOARD][POST][CREATE] Request for create new board");

        //check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            //Create board
            final Board board = this.boardService.create(this.boardMapper.mapBoardReqDtoToBoard(boardReqDto));

            //Find user
            final UUID userId = UUID.fromString(boardReqDto.getUserId());

            final User user = this.userService.findById(userId).orElseThrow(() -> new Exception("User not found"));

            //Create BoardRole
            final BoardInfo boardInfo = this.boardRoleService.create(
                    this.boardMapper.mapBoardAndUserToBoardRole(board, user));

            this.log.info("[BOARD][POST][CREATE]Board created successfully");

            return ResponseEntity.ok().body(this.boardMapper.mapBoardInfoToBoardResDto(boardInfo));

        } catch (Exception e) {
            this.log.error("[BOARD][POST][CREATE] Error creating new board : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BoardResDto>> getUserBoards(@RequestHeader("Authorization") final String userToken,
                                                           @PathVariable String userId) throws Exception {
        this.log.info("[BOARD][GET][BY USER] Request for get user boards");

        //check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final List<BoardResDto> boardList = new ArrayList<>();
        final User user = this.userService.findById(
                UUID.fromString(userId)).orElseThrow(() -> new Exception("User not found"));

        final List<BoardInfo> userBoardInfos = this.boardRoleService.getBoardRolesByUserId(user);

        for (BoardInfo br : userBoardInfos) {
            boardList.add(this.boardMapper.mapBoardInfoToBoardResDto(br));
        }

        this.log.info("[BOARD][POST][BY USER]Board list obtained successfully");
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/{userId}/{boardId}")
    public ResponseEntity<BoardResDto> getBoardDetails(@RequestHeader("Authorization") final String userToken,
                                                       @PathVariable String userId,
                                                       @PathVariable String boardId) throws Exception {
        this.log.info("[BOARD][GET][BY ID] Request for get board details");

        //check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final User user = userService.findById(UUID.fromString(userId)).orElse(new User());

        final BoardInfo boardInfo = this.boardRoleService
                .getBoardRoleById(UUID.fromString(boardId)).orElseThrow(() -> new Exception("Board not found"));

        if (user.getUserId() != null && user.getUserId() == boardInfo.getUser().getUserId()) {
            this.log.info("[BOARD][GET][BY ID]Board details obtained succesfully. Returning board info.");
            return ResponseEntity.ok(this.boardMapper.mapBoardInfoToBoardResDto(boardInfo));
        }

        this.log.error("[BOARD][GET][BY ID] Requesting user has no relationship with requested board");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
