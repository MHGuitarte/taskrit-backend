package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.ListReqDto;
import com.mangh.taskrit.dto.response.ListResDto;
import com.mangh.taskrit.model.Board;
import com.mangh.taskrit.model.List;

public interface ListMapper {

    List mapListReqDtoAndBoardToList(final ListReqDto listReqDto, final Board board);

    ListResDto mapListToListResDto(final List list);

    public java.util.List<ListResDto> mapListsToListsResDtos(final java.util.List<List> lists);
}
