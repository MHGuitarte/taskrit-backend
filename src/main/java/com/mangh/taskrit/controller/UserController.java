package com.mangh.taskrit.controller;

import com.mangh.taskrit.dto.request.UserLoginReqDto;
import com.mangh.taskrit.dto.request.UserRegisterReqDto;
import com.mangh.taskrit.dto.response.UserRegisterResDto;
import com.mangh.taskrit.mapper.poji.UserMapper;
import com.mangh.taskrit.model.User;
import com.mangh.taskrit.service.poji.UserService;
import com.mangh.taskrit.util.poji.EmailService;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${user.token.expiration}")
    private long EXPIRATION_TOKEN; //10 min expiration

    @Value("${admin.token.access.expiration}")
    private long EXPIRATION_ADMIN_TOKEN; //3 min expiration

    @Value("${admin.token.access.security}")
    private String adminAuth;

    private final Logger log = new Logger(UserController.class.getName());

    private final UserMapper userMapper;
    private final UserService userService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtils jwtTokenUtils;

    public UserController(UserService userService, UserMapper userMapper, EmailService emailService, AuthenticationManager authenticationManager, JWTTokenUtils jwtTokenUtils) {
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserRegisterResDto saveUser(@RequestBody UserRegisterReqDto userRegisterReqDto) throws MessagingException {
        this.log.info("[USER][POST][NEW]Request for register new user");

        final User user = this.userMapper.mapUserRegisterReqToUser(userRegisterReqDto);
        final User createdUser = this.userService.create(user);

        this.log.info("[USER][POST][NEW] User {} successfully added. Sending confirmation mail to user",
                user.getUserId().toString());

        this.sendRegistrationMail(createdUser); //TODO: Escribir mail

        return this.userMapper.mapUserToUserRegisterRes(createdUser);
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLoginReqDto userLoginReqDto, HttpServletResponse response) {
        this.log.info("[USER][POST][LOGIN]Request for login with username {}", userLoginReqDto.getUsername());

        try {

                Authentication authentication = this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLoginReqDto.getUsername(), userLoginReqDto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                final User user = (User) authentication.getPrincipal();

                String userToken = jwtTokenUtils.getJWTToken(user);
//TODO: ME QUEDE AQuÍ
            response.addHeader("Authorization", userToken);

        } catch (UsernameNotFoundException e) {
            this.log.error("[USER][POST][LOGIN]Request for login failed : {}", e.getMessage());
        }
        //Check if user exists -- DONE


        //Compare passwords -- DONE

        //build token

        //return token in header
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        final User user = userRepository.findByUserName(username);
        return this.userMapper.mapUserToUserInfoDto(user);
    }


    //private methods
    private void sendRegistrationMail(final User user) throws MessagingException {
        this.emailService.sendSimpleMessage(user.getEmail(),
                "Tu cuenta en TaskrIt ha sido creada correctamente",
                "patata");
    }
}
