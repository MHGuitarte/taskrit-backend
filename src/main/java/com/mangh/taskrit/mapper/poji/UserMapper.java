package com.mangh.taskrit.mapper.poji;

import com.mangh.taskrit.dto.request.UserLoginReqDto;
import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.model.User;

public interface UserMapper {

    User mapUserRegisterReqToUser(final UserRegisterReqDto userRegisterReqDto);

    UserRegisterResDto mapUserToUserRegisterRes(final User user);

    Boolean checkPasswords(User user, UserLoginReqDto request);
}
