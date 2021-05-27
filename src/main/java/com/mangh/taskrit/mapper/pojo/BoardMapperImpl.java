package com.mangh.taskrit.mapper.pojo;

import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.mapper.poji.BoardMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board mapBoardReqDtoToBoard(BoardReqDto boardReqDto) {
        final Board board = new Board();

        board.setName(boardReqDto.getName());
        board.setDescription(boardReqDto.getDescription());

        return board;
    }

    @Override
    public BoardRole mapBoardAndUserToBoardRole(User user, Board board) {
        final BoardRole boardRole = new BoardRole();

        boardRole.setUser(user);
        boardRole.setBoard(board);

        return boardRole;
    }
}
