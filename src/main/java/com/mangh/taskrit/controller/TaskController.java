package com.mangh.taskrit.controller;

import com.mangh.taskrit.util.pojo.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
public class TaskController {

    private final Logger log = new Logger(BoardController.class.getName());

    //basic operations first, better implementations later

    // GET get task details

    // POST create task (need list to create)

    // PUT modify task

    // PUT set remaining

}
