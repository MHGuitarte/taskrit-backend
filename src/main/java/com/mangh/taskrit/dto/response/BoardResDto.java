package com.mangh.taskrit.dto.response;

import com.mangh.taskrit.model.Board;
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

    private Board board;
}
