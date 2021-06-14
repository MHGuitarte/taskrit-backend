package com.mangh.taskrit.dto.response;


import com.mangh.taskrit.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResDto implements Serializable {

    private UUID userId;
    private String username;
    private UserRole role;
}
