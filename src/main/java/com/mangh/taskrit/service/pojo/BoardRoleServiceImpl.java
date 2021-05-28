package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.repository.BoardRoleRepository;
import com.mangh.taskrit.service.poji.BoardRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardRoleServiceImpl implements BoardRoleService {

    private BoardRoleRepository boardRoleRepository;

    public BoardRoleServiceImpl(BoardRoleRepository boardRoleRepository) {
        this.boardRoleRepository = boardRoleRepository;
    }

    @Override
    public BoardInfo create(final BoardInfo boardInfo) {
        return this.boardRoleRepository.save(boardInfo);
    }

    @Override
    public List<BoardInfo> getBoardRolesByUserId(final User user) {
        return boardRoleRepository.findAllByUser(user);
    }

    @Override
    public Optional<BoardInfo> getBoardRoleById(final UUID boardRoleId) {
        return this.boardRoleRepository.findById(boardRoleId);
    }
}
