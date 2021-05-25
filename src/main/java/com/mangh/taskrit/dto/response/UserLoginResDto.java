package com.mangh.taskrit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResDto {

    private String id;
    private String username;
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
}
