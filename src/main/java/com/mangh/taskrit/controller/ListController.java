package com.mangh.taskrit.controller;

import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "*")
public class ListController {

    private final Logger log = new Logger(BoardController.class.getName());

    //basic operations first, better implementations later

    //POST create list

    //DELETE delete list

}
