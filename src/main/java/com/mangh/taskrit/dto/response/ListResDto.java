package com.mangh.taskrit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResDto implements Serializable {

    private String id;
    private String name;
    private String description;
    private String boardId;
    private java.util.List<TaskResDto> tasks = new ArrayList<>();

    //colour will remain hidden for now
}
