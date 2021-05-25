package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.response.BoardResDto;
import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.BoardRoleService;
import com.mangh.taskrit.service.poji.BoardService;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final Logger log = new Logger(BoardController.class.getName());

    private JWTAuthorizationToken jwtAuthorizationToken;
    private BoardRoleService boardRoleService;
    private BoardService boardService;
    private UserService userService;

    public BoardController(JWTAuthorizationToken jwtAuthorizationToken, BoardRoleService boardRoleService, BoardService boardService, UserService userService) {

        this.jwtAuthorizationToken = jwtAuthorizationToken;
        this.boardRoleService = boardRoleService;
        this.boardService = boardService;
        this.userService = userService;
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

        List<BoardRole> userBoardRoles = this.boardRoleService.getBoardRolesByUserId(user);

        for(BoardRole br : userBoardRoles) {
            //TODO pillar boards, mapearlos junto a los roles en la respuesta y meterlo en boardList
        }

        return ResponseEntity.ok(boardList); //TODO finally clause?
    }
}
