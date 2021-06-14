package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardService {

    Board create (final Board board);

    List<Board> getBoardsByUserAndRole (final BoardInfo boardInfo);

    Optional<Board> getBoardById (final UUID boardId);
}
