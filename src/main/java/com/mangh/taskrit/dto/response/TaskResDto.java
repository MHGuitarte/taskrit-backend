package com.mangh.taskrit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResDto implements Serializable {

    private String id;
    private String title;
    private String description;
    private String responsibleId;
    private Integer effort;
    private Double estimate;
    private Double pending;
    private String listId;

}
