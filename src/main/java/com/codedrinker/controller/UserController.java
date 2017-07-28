package com.codedrinker.controller;

import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.entity.User;
import com.codedrinker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    Object createUser(@RequestBody User user) {
        ResponseDTO responseDTO = userService.save(user);
        return responseDTO;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    Object show(@PathVariable(name = "id") String id, @RequestParam(name = "appId") String appId) {
        return null;
    }
}
