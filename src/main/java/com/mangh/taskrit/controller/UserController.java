package com.mangh.taskrit.controller;

import com.mangh.taskrit.model.User;
import com.mangh.taskrit.util.poji.EmailService;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    private final JWTTokenUtils jwtTokenUtils;

    public UserController(UserRepository userRepository, EmailService emailService, JWTTokenUtils jwtTokenUtils) {
        this.emailService = emailService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/new")
    public void saveUser(@RequestBody UserRegisterDto userRegisterDto) {
        final User user = this.userMapper.mapUserRegisterToUser(userRegisterDto);
        userRepository.save(user);
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLoginDto userLogindDto, HttpServletResponse response) {
        final User user = this.userMapper.mapUserLoginDtoToUser(userLogindDto);

        //Check if user exists

        //Compare passwords

        //build token

        //return token in header
        response.addHeader("Authorization", );
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        final User user = userRepository.findByUserName(username);
        return this.userMapper.mapUserToUserInfoDto(user);
    }

}
