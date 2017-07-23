package com.codedrinker.controller;

import com.codedrinker.entity.Comment;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    Object createComment(@RequestBody Comment comment) {
        ResponseDTO responseDTO = commentService.save(comment);
        return responseDTO;
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    @ResponseBody
    Object show(@PathVariable(name = "id") String id, @RequestParam(name = "appId") String appId) {
        return null;
    }

    @RequestMapping("/comments")
    @ResponseBody
    Object activities(@RequestParam(name = "appId", required = false, defaultValue = "") String appId,
                      @RequestParam(name = "pageId", required = false, defaultValue = "") String pageId) {
        return null;
    }
}
