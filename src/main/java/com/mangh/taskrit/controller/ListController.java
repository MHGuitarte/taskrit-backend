package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.ListReqDto;
import com.mangh.taskrit.dto.response.ListResDto;
import com.mangh.taskrit.mapper.poji.BoardMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.service.poji.BoardService;
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
    private BoardService boardService;
    private BoardMapper boardMapper;
    private ListService listService;

    public ListController(JWTAuthorizationToken jwtAuthorizationToken) {
        this.jwtAuthorizationToken = jwtAuthorizationToken;
    }


    @PostMapping("/create")
    public ResponseEntity<ListResDto> createList(@RequestHeader("Authorization") final String userToken,
                                                 @RequestBody ListReqDto listReqDto) throws Exception {

        //check token
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //get board to insert list into
        final Board board = this.boardService
                .getBoardById(UUID.fromString(listReqDto.getBoardId()))
                .orElseThrow(() -> new Exception("Target board not found"));

        //map list and create it

        //TODO: finish this and continue with list service (has some fails)

        //return new list

        return ResponseEntity.ok().build();
    }

    //basic operations first, better implementations later

    //POST create list

    //DELETE delete list

}
