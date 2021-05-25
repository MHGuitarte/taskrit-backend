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

    private String username;
    private String email;
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
}
