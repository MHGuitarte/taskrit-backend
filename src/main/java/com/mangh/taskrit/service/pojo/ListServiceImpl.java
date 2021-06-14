package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;
import com.mangh.taskrit.repository.ListRepository;
import com.mangh.taskrit.service.poji.ListService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListServiceImpl implements ListService {

    private ListRepository listRepository;

    public ListServiceImpl(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Override
    public List create(List list) {
        return this.listRepository.save(list);
    }

    @Override
    public java.util.List<List> getListsByBoard(Board board) {
        return this.listRepository.getListsByBoard(board);
    }

    @Override
    public Optional<List> getListById(UUID listId) {
        return this.listRepository.findById(listId);
    }
}
