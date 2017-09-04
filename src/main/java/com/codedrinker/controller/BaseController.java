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

    protected String cookiesKey(HttpServletRequest request) {
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

    protected ResponseDTO checkAccessToken(HttpServletRequest request) throws CommentHubException {
        ResponseDTO responseDTO = null;
        String cookiesKey = cookiesKey(request);
        if (StringUtils.isNotBlank(cookiesKey)) {
            try {
                responseDTO = authorizationService.getByAccessToken(AESSecurityUtil.decrypt(cookiesKey, key));
            } catch (Exception e) {
                logger.error("decrypt error -> {}", cookiesKey, e);
                request.getSession().removeAttribute("id");
                request.getSession().removeAttribute("name");
                throw new CommentHubException("Authorization Failed.");
            }
            UserDTO userDTO = (UserDTO) responseDTO.getData();
            request.getSession().setAttribute("id", userDTO.getId());
            request.getSession().setAttribute("name", userDTO.getName());
        }
        return responseDTO;
    }
}
