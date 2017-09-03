package com.codedrinker.controller;

import com.codedrinker.dto.UserDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.service.AuthorizationService;
import com.codedrinker.utils.AESSecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 29/08/2017.
 */
public class BaseController {
    @Value("${aes.key}")
    private String key;

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private AuthorizationService authorizationService;

    protected String cookiesUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String user = null;
        if (cookies != null && cookies.length != 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (StringUtils.equals(cookies[i].getName(), "user")) {
                    user = cookies[i].getValue();
                }
            }
        }
        return user;
    }

    protected void checkAccessToken(HttpServletRequest request) throws CommentHubException {
        String user = cookiesUser(request);
        if (StringUtils.isNotBlank(user)) {
            ResponseDTO responseDTO = null;
            try {
                responseDTO = authorizationService.getByAccessToken(AESSecurityUtil.decrypt(user, key));
            } catch (Exception e) {
                logger.error("decrypt error -> {}", user, e);
                request.getSession().removeAttribute("id");
                request.getSession().removeAttribute("name");
                throw new CommentHubException("Authorization Failed.");
            }
            request.getSession().setAttribute("id", user);
            UserDTO userDTO = (UserDTO) responseDTO.getData();
            request.getSession().setAttribute("name", userDTO.getName());
        }
    }
}
