package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.BoardRole;
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
    public BoardRole create(final BoardRole boardRole) {
        return this.boardRoleRepository.save(boardRole);
    }

    @Override
    public List<BoardRole> getBoardRolesByUserId(final User user) {
        return boardRoleRepository.findAllByUser(user);
    }

    @Override
    public Optional<BoardRole> getBoardRoleById(final UUID boardRoleId) {
        return this.boardRoleRepository.findById(boardRoleId);
    }
}
