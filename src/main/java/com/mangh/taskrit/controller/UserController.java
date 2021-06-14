package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.UserLoginReqDto;
import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserLoginResDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.mapper.poji.UserMapper;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final Logger log = new Logger(UserController.class.getName());

    private final UserMapper userMapper;
    private final UserService userService;
    private final JWTTokenUtils jwtTokenUtils;
    private JWTAuthorizationToken jwtAuthorizationToken;

    public UserController(UserService userService, UserMapper userMapper,
                          JWTTokenUtils jwtTokenUtils, JWTAuthorizationToken jwtAuthorizationToken) {
        this.userMapper = userMapper;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
        this.jwtAuthorizationToken = jwtAuthorizationToken;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResDto> saveUser(@RequestBody UserRegisterReqDto userRegisterReqDto) {
        this.log.info("[USER][POST][NEW]Request for register new user");

        // map and create user

        final User user = this.userMapper.mapUserRegisterReqToUser(userRegisterReqDto);
        final User createdUser = this.userService.create(user);

        this.log.info("[USER][POST][NEW] User {} successfully added. Sending confirmation mail to user",
                user.getUserId().toString());

        return ResponseEntity.ok(this.userMapper.mapUserToUserRegisterRes(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResDto> login(@RequestBody UserLoginReqDto userLoginReqDto) {
        this.log.info("[USER][POST][LOGIN]Request for login with username {}", userLoginReqDto.getUsername());

        try {
            //get user
            final User user = this.userService.findByUsername(userLoginReqDto.getUsername())
                    .orElse(new User());

            //check passwords
            if (!this.userMapper.checkPasswords(user, userLoginReqDto).booleanValue()) {
                this.log.error("[USER][POST][LOGIN]Username or password incorrect");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (!user.isEnabled()) {
                this.log.error("[USER][POST][LOGIN]User is not enabled for login");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            this.log.info("[USER][POST][LOGIN]Login was successful. Returning access token");

            //build token
            String userToken = jwtTokenUtils.getJWTToken(user, userLoginReqDto.getSaveLogin());

            this.log.info("[USER][POST][LOGIN]User {} successfully logged. Sending user info", user.getUsername());

            return ResponseEntity.ok()
                    .header("Authorization", userToken) //
                    .body(UserLoginResDto.builder() //
                            .id(user.getUserId().toString()) //
                            .username(user.getUsername()) //
                            .token(userToken) //
                            .saveLogin(userLoginReqDto.getSaveLogin()) //
                            .build());

        } catch (UsernameNotFoundException e) {
            this.log.error("[USER][POST][LOGIN]Request for login failed : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/checkToken")
    public ResponseEntity<Boolean> checkToken(@RequestHeader("Authorization") final String userToken) {
        if (!this.jwtAuthorizationToken.checkToken(userToken)) {
            this.log.error("Unauthorized webtoken provided".toUpperCase());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        return ResponseEntity.ok(true);
    }

}
