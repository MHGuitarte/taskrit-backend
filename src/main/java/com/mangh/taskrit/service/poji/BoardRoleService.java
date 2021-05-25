package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardRoleService {

    BoardRole create(final BoardRole boardRole);

    List<BoardRole> getBoardRolesByUserId(final User user);

    BoardRole getBoardRole(final String boardRoleId);
}
