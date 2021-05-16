package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.model.User;

public interface UserMapper {

    public User mapUserRegisterReqToUser(final UserRegisterReqDto userRegisterReqDto);

    public UserRegisterResDto mapUserToUserRegisterRes(final User user);
}
