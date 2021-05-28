package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.BoardInfo;
import com.mangh.taskrit.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BoardRoleService {

    BoardInfo create(final BoardInfo boardInfo);

    List<BoardInfo> getBoardRolesByUserId(final User user);

    Optional<BoardInfo> getBoardRoleById(final UUID boardRoleId);
}
