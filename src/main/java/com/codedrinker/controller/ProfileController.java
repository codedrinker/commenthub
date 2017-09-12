package com.codedrinker.controller;

import com.codedrinker.dto.UserDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.service.AuthorizationService;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${github.client.id}")
    private String clientId;

    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("clientId", clientId);
            ResponseDTO responseDTO = checkAccessToken(request);
            if (responseDTO == null) {
                model.addAttribute("error", "Something Wrong, Ple retry.");
                return "profile";
            }
            UserDTO userDTO = (UserDTO) responseDTO.getData();
            ResponseDTO dbResponseDTO = authorizationService.get(userDTO.getId());
            UserDTO data = (UserDTO) (dbResponseDTO.getData());
            model.addAttribute("website", data.getWebsite());
        } catch (CommentHubException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String update(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("clientId", clientId);
            String website = request.getParameter("website");
            if (StringUtils.isBlank(website)) {
                model.addAttribute("error", "Website can not be blank.");
                return "profile";
            }
            ResponseDTO responseDTO = checkAccessToken(request);
            if (responseDTO == null) {
                model.addAttribute("error", "Something Wrong, Ple retry.");
                return "profile";
            }
            UserDTO userDTO = (UserDTO) responseDTO.getData();
            authorizationService.saveOrUpdate(userDTO.getId(), website, userDTO.getToken());
        } catch (CommentHubException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        return "redirect:profile";
    }
}
