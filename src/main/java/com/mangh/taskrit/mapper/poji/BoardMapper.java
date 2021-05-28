package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.BoardReqDto;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;

public interface BoardMapper {

    Board mapBoardReqDtoToBoard (final BoardReqDto boardReqDto);

    BoardRole mapBoardAndUserToBoardRole(final Board board, final User user);
}
