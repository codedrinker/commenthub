package com.codedrinker.controller;

import com.codedrinker.exception.CommentHubException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 06/08/2017.
 */
@Controller
public class IndexController extends BaseController {

    @Value("${github.client.id}")
    private String clientId;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("clientId", clientId);
        try {
            checkAccessToken(request);
        } catch (CommentHubException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }
}
