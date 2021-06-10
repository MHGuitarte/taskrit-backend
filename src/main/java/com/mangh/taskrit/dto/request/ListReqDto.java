package com.mangh.taskrit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListReqDto implements Serializable {

    private String boardId;
    private String name;
    private String description;
}
