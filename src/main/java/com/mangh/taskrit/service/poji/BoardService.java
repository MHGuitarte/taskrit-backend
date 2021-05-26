package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardService {

    Board create (final Board board);

    List<Board> getBoardsByUserAndRole (final BoardRole boardRole);

    Optional<Board> getBoardById (final UUID boardId);
}
