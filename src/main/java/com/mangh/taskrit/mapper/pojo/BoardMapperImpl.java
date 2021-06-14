package com.mangh.taskrit.mapper.pojo;

import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.dto.response.BoardResDto;
import com.mangh.taskrit.mapper.poji.BoardMapper;
import com.mangh.taskrit.mapper.poji.ListMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import org.springframework.stereotype.Component;

@Component
public class BoardMapperImpl implements BoardMapper {

    private ListMapper listMapper;

    public BoardMapperImpl(ListMapper listMapper) {
        this.listMapper = listMapper;
    }

    @Override
    public Board mapBoardReqDtoToBoard(BoardReqDto boardReqDto) {
        final Board board = new Board();

        board.setName(boardReqDto.getName());
        board.setDescription(boardReqDto.getDescription());

        return board;
    }

    @Override
    public BoardInfo mapBoardAndUserToBoardRole(final Board board, final User user) {
        final BoardInfo boardInfo = new BoardInfo();

        boardInfo.setUser(user);
        boardInfo.setBoard(board);

        return boardInfo;
    }

    @Override
    public BoardResDto mapBoardInfoToBoardResDto(BoardInfo boardInfo) {
        return BoardResDto.builder()
                .boardId(boardInfo.getBoardInfoId().toString())
                .boardName(boardInfo.getBoard().getName())
                .boardDescription(boardInfo.getBoard().getDescription())
                .boardRole(boardInfo.getRoleType())
                .boardLists(this.listMapper.mapListsToListsResDtos(boardInfo.getBoard().getLists()))
                .build();
    }
}
