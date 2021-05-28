package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BoardRoleService {

    BoardRole create(final BoardRole boardRole);

    List<BoardRole> getBoardRolesByUserId(final User user);

    Optional<BoardRole> getBoardRoleById(final UUID boardRoleId);
}
