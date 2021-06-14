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
public class TaskReqDto implements Serializable {

    private String listId;
    private String title;
    private String description;
    private Integer effort;
    private Double estimate;

}
