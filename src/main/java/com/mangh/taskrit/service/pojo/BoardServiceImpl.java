package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.service.poji.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    @Override
    public Board create(Board board) {
        return null;
    }

    @Override
    public List<Board> getBoardsByUserAndRole(BoardRole boardRole) {
        return null;
    }

    @Override
    public Board getBoard(UUID boardId) {
        return null;
    }
}
