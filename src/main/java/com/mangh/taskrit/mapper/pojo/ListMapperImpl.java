package com.mangh.taskrit.mapper.pojo;

import com.mangh.taskrit.dto.request.ListReqDto;
import com.mangh.taskrit.dto.response.ListResDto;
import com.mangh.taskrit.mapper.poji.ListMapper;
import com.mangh.taskrit.mapper.poji.TaskMapper;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListMapperImpl implements ListMapper {

    private TaskMapper taskMapper;

    public ListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public List mapListReqDtoAndBoardToList(final ListReqDto listReqDto, final Board board) {
        final List list = new List();

        list.setName(listReqDto.getName());
        list.setDescription(listReqDto.getDescription());
        list.setBoard(board);

        return list;
    }

    @Override
    public ListResDto mapListToListResDto(final List list) {
        return ListResDto.builder()
                .id(list.getListId().toString())
                .name(list.getName())
                .description(list.getDescription())
                .boardId(list.getBoard().getBoardId().toString())
                .tasks(this.taskMapper.mapTaskListToTaskResDtoList(list.getTasks()))
                .build();
    }

    @Override
    public java.util.List<ListResDto> mapListsToListsResDtos(java.util.List<List> lists) {
        return lists.stream().map(this::mapListToListResDto).collect(Collectors.toList());
    }


}
