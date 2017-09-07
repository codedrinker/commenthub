package com.codedrinker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by codedrinker on 06/09/2017.
 */
@Controller
public class CommentController extends BaseController {

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public String comments(Model model,
                           @RequestParam(value = "commenthub_website") String commenthub_website,
                           @RequestParam(value = "commenthub_identifier") String commenthub_identifier) {
        model.addAttribute("commenthub_website", commenthub_website);
        model.addAttribute("commenthub_identifier", commenthub_identifier);
        return "comment";
    }
}
