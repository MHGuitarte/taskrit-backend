package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.dto.response.BoardResDto;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;

public interface BoardMapper {

    Board mapBoardReqDtoToBoard(final BoardReqDto boardReqDto);

    BoardInfo mapBoardAndUserToBoardRole(final Board board, final User user);

    BoardResDto mapBoardInfoToBoardResDto(final BoardInfo boardInfo);
}
