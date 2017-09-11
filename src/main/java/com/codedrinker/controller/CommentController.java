package com.codedrinker.controller;

import com.codedrinker.exception.CommentHubException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 06/09/2017.
 */
@Controller
public class CommentController extends BaseController {

    @Value("${github.client.id}")
    private String clientId;

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public String comments(Model model,
                           @RequestParam(value = "commenthub_website") String commenthub_website,
                           @RequestParam(value = "commenthub_identifier") String commenthub_identifier,
                           HttpServletRequest request) {
        model.addAttribute("commenthub_website", commenthub_website);
        model.addAttribute("commenthub_identifier", commenthub_identifier);
        model.addAttribute("clientId", clientId);
        try {
            checkAccessToken(request);
        } catch (CommentHubException e) {
        }
        return "comment";
    }
}
