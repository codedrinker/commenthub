package com.codedrinker.controller;

import com.codedrinker.exception.CommentHubException;
import com.codedrinker.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProfileController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Value("${github.client.id}")
    private String clientId;

    @Value("${aes.key}")
    private String key;

    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("clientId", clientId);
            checkAccessToken(request);
        } catch (CommentHubException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        return "index";
    }
}
