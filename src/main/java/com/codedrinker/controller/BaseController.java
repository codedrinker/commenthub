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
import javax.servlet.http.HttpServletResponse;

/**
 * Created by codedrinker on 29/08/2017.
 */
public class BaseController {
    @Value("${aes.key}")
    private String key;

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private AuthorizationService authorizationService;

    protected String getUserCookie(HttpServletRequest request) {
        return getCookie(request, "user");
    }

    protected void setUserCookie(HttpServletResponse response, String user) {
        Cookie cookie = new Cookie("user", user);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(cookie);
    }

    protected String getCookie(HttpServletRequest request, String key) {
        Cookie cookieEntry = getCookieEntry(request, key);
        if (cookieEntry != null) {
            return cookieEntry.getValue();
        } else {
            return null;
        }
    }

    protected Cookie getCookieEntry(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (StringUtils.equals(cookies[i].getName(), key)) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    public void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie cookie = getCookieEntry(request, key);
        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    protected ResponseDTO checkAccessToken(HttpServletRequest request) throws CommentHubException {
        ResponseDTO responseDTO = null;
        String cookiesKey = getUserCookie(request);
        if (StringUtils.isNotBlank(cookiesKey)) {
            try {
                responseDTO = authorizationService.getByAccessToken(AESSecurityUtil.decrypt(cookiesKey, key));
            } catch (Exception e) {
                logger.error("decrypt error -> {}", cookiesKey, e);
                request.getSession().removeAttribute("id");
                request.getSession().removeAttribute("name");
                throw new CommentHubException("Authorization Failed.");
            }
            if (responseDTO == null) return null;
            UserDTO userDTO = (UserDTO) responseDTO.getData();
            request.getSession().setAttribute("id", userDTO.getId());
            request.getSession().setAttribute("name", userDTO.getName());
        }
        return responseDTO;
    }
}
