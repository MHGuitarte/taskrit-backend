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
public class BoardReqDto implements Serializable {

    private String userId;
    private String boardId;
    private String name;
    private String description;


}
