package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.dto.response.BoardResDto;
import com.mangh.taskrit.mapper.poji.BoardMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.BoardRoleService;
import com.mangh.taskrit.service.poji.BoardService;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private BoardRoleService boardRoleService;
    private BoardService boardService;
    private UserService userService;
    private BoardMapper boardMapper;

    public BoardController(JWTAuthorizationToken jwtAuthorizationToken, BoardRoleService boardRoleService, BoardService boardService, UserService userService, BoardMapper boardMapper) {

        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.boardRoleService = boardRoleService;
        this.boardService = boardService;
        this.userService = userService;
        this.boardMapper = boardMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<BoardRole> createBoard(@RequestHeader("Authorization") final String userToken,
                                                 @RequestBody BoardReqDto boardReqDto) {

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

            System.out.println(userId);

            final User user = this.userService.findById(userId).orElseThrow(() -> new Exception("User not found"));

            //Create BoardRole
            final BoardRole boardRole = this.boardRoleService.create(
                    this.boardMapper.mapBoardAndUserToBoardRole(board, user));

            System.out.println(boardRole);

            //BuildResponse
            //TODO: ALGO PASA CON ESTA LLAMADA QUE PETA DESPUES DE CREAR EL BOARD ROLE

            return ResponseEntity.ok().body(boardRole);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BoardResDto>> getBoardsByUser(@RequestHeader("Authorization") final String userToken,
                                                             @PathParam("userId") final String userId) throws Exception {
        //check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final List<BoardResDto> boardList = new ArrayList<>();

        final User user = this.userService.findById(UUID.fromString(userId)).orElseThrow(() -> new Exception("User not found"));

        final List<BoardRole> userBoardRoles = this.boardRoleService.getBoardRolesByUserId(user);

        for (BoardRole br : userBoardRoles) {
            Optional<Board> board = this.boardService.getBoardById(br.getBoard().getBoardId());
            //TODO pillar boards, mapearlos junto a los roles en la respuesta y meterlo en boardList
        }

        return ResponseEntity.ok(boardList); //TODO finally clause?
    }
}
