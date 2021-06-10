package com.mangh.taskrit.dto.response;

import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResDto implements Serializable {

    private String boardId;
    private String boardName;
    private String boardDescription;
    private BoardRole boardRole;
    private java.util.List<ListResDto> boardLists;

}
