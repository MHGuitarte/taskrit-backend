package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;

import java.util.Optional;
import java.util.UUID;

public interface ListService {

    List create(final List list);

    java.util.List<List> getListsByBoard(final Board board);

    Optional<List> getListById(final UUID listId);
}
