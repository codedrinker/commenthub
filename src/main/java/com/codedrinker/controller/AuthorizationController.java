package com.codedrinker.controller;

import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.github.entity.GitHubUser;
import com.codedrinker.service.AuthorizationService;
import com.codedrinker.utils.AESSecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Controller
@RequestMapping(value = "authorizations")
public class AuthorizationController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Value("${aes.key}")
    private String key;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        ResponseDTO responseDTO = authorizationService.callback(code);
        String redirectUri = getCookie(request, "commenthub_redirect");
        if (responseDTO.isOK()) {
            GitHubUser gitHubUser = (GitHubUser) responseDTO.getData();
            String user = null;
            try {
                user = AESSecurityUtil.encrypt(gitHubUser.getAccess_token(), key);
            } catch (Exception e) {
                logger.error("decrypt error id -> {}", gitHubUser.getId(), e);
            }
            setUserCookie(response, user);
            setIdCookie(response, gitHubUser.getId());
            return StringUtils.isNotBlank(redirectUri) ? "redirect:" + redirectUri : "redirect:/";
        } else {
            return StringUtils.isNotBlank(redirectUri) ? "redirect:" + redirectUri : "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    Object create(@RequestBody Authorization authorization) {
        logger.info("save authorization ->{}", authorization);
        ResponseDTO responseDTO = authorizationService.save(authorization);
        return responseDTO;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    Object update(@RequestBody Authorization authorization) {
        logger.info("update by authorization ->{}", authorization);
        ResponseDTO responseDTO = authorizationService.update(authorization);
        return responseDTO;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    Object delete(@PathVariable(value = "id") Integer id) {
        logger.info("delete by id ->{}", id);
        authorizationService.delete(id);
        return ResponseDTO.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    Object get(@PathVariable(value = "id") Integer id) {
        logger.info("get by id ->{}", id);
        ResponseDTO responseDTO = authorizationService.get(id);
        return responseDTO;
    }
}
