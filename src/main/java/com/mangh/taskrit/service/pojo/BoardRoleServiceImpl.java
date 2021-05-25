package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.repository.BoardRoleRepository;
import com.mangh.taskrit.service.poji.BoardRoleService;

import java.util.List;

public class BoardRoleServiceImpl implements BoardRoleService {

    private BoardRoleRepository boardRoleRepository;

    public BoardRoleServiceImpl(BoardRoleRepository boardRoleRepository) {
        this.boardRoleRepository = boardRoleRepository;
    }

    @Override
    public BoardRole create(final BoardRole boardRole) {
        return null;
    }

    @Override
    public List<BoardRole> getBoardRolesByUserId(final User user) {
        return boardRoleRepository.findAllByUser(user);
    }

    @Override
    public BoardRole getBoardRole(String boardRoleId) {
        return null;
    }
}
