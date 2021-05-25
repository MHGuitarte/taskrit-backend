package com.mangh.taskrit.mapper.pojo;

import com.mangh.taskrit.dto.request.UserLoginReqDto;
import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.mapper.poji.UserMapper;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.model.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User mapUserRegisterReqToUser(UserRegisterReqDto userRegisterReqDto) {
        final User user = new User();

        user.setUsername(userRegisterReqDto.getUsername());
        user.setEmail(userRegisterReqDto.getEmail());
        user.setPassword(encoder.encode(userRegisterReqDto.getPassword()));
        user.setRole(UserRole.USER);

        return user;
    }

    @Override
    public UserRegisterResDto mapUserToUserRegisterRes(User user) {
        return UserRegisterResDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public Boolean checkPasswords(User user, UserLoginReqDto request) {
        return this.encoder.matches(request.getPassword(), user.getPassword());
    }
}
