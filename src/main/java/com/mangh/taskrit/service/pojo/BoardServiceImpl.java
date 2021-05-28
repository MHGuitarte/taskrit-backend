package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.repository.BoardRepository;
import com.mangh.taskrit.service.poji.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board create(Board board) {
        return this.boardRepository.save(board);
    }

    @Override
    public List<Board> getBoardsByUserAndRole(BoardInfo boardInfo) {
        return null;
    }

    @Override
    public Optional<Board> getBoardById(UUID boardId) {
        return this.boardRepository.findById(boardId);
    }
}
