package com.mangh.taskrit.controller;

import com.mangh.taskrit.configuration.JWTAuthorizationToken;
import com.mangh.taskrit.dto.request.UserLoginReqDto;
import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserLoginResDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.mapper.poji.UserMapper;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.poji.EmailService;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final Logger log = new Logger(UserController.class.getName());

    private final UserMapper userMapper;
    private final UserService userService;
    private final EmailService emailService;
    private final JWTTokenUtils jwtTokenUtils;
    private JWTAuthorizationToken jwtAuthorizationToken;

    public UserController(UserService userService, UserMapper userMapper, EmailService emailService,
                          JWTTokenUtils jwtTokenUtils, JWTAuthorizationToken jwtAuthorizationToken) {
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
        this.jwtAuthorizationToken = jwtAuthorizationToken;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResDto> saveUser(@RequestBody UserRegisterReqDto userRegisterReqDto) {
        this.log.info("[USER][POST][NEW]Request for register new user");

        final User user = this.userMapper.mapUserRegisterReqToUser(userRegisterReqDto);
        final User createdUser = this.userService.create(user);

        this.log.info("[USER][POST][NEW] User {} successfully added. Sending confirmation mail to user",
                user.getUserId().toString());

        return ResponseEntity.ok(this.userMapper.mapUserToUserRegisterRes(createdUser));
/*
        try {
            this.sendRegistrationMail(createdUser); //TODO: AuthenticationFailedException a la hora de mandar correo

        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body("Mail Service Unavailable, registration mail will not be sended.");
        }

*/
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResDto> login(@RequestBody UserLoginReqDto userLoginReqDto) {
        this.log.info("[USER][POST][LOGIN]Request for login with username {}", userLoginReqDto.getUsername());

        try {
            final User user = this.userService.findByUsername(userLoginReqDto.getUsername());

            if (!this.userMapper.checkPasswords(user, userLoginReqDto)) {
                this.log.error("[USER][POST][LOGIN]Username or password incorrect");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String userToken = jwtTokenUtils.getJWTToken(user, userLoginReqDto.getSaveLogin());

            return ResponseEntity.ok()
                    .header("Authorization", userToken) //
                    .body(UserLoginResDto.builder() //
                            .id(user.getUserId().toString()) //
                            .username(user.getUsername()) //
                            .token(userToken) //
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

    //private methods
    private void sendRegistrationMail(final User user) throws MessagingException {
        this.emailService.sendSimpleMessage(user.getEmail(),
                "Tu cuenta en TaskrIt ha sido creada correctamente",
                "patata");
    }
}
