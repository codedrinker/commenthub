package com.codedrinker.controller;

import com.codedrinker.dto.CommentDTO;
import com.codedrinker.dto.UserDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 06/09/2017.
 */
@Controller
public class CommentController extends BaseController {

    @Value("${github.client.id}")
    private String clientId;

    @Autowired
    private CommentService commentService;

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

    @ResponseBody
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Object post(HttpServletRequest request, @RequestBody CommentDTO commentDTO) {
        try {
            ResponseDTO responseDTO = checkAccessToken(request);
            UserDTO data = (UserDTO) responseDTO.getData();
            if (data != null) {
                String token = data.getToken();
                ResponseDTO comment = commentService.createComment(token, commentDTO);
                return comment;
            } else {
                return ResponseDTO.error("Timeout.");
            }
        } catch (CommentHubException e) {
        }
        return ResponseDTO.ok(null);
    }
}
