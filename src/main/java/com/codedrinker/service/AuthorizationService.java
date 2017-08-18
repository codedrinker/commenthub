package com.codedrinker.service;

import com.codedrinker.converter.UserConverter;
import com.codedrinker.dao.AuthorizationDao;
import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.github.GitHubAuthorizationApi;
import com.codedrinker.github.GitHubUserApi;
import com.codedrinker.github.entity.GitHubUser;
import com.codedrinker.utils.TimestampUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 23/07/2017.
 */
@Component
public class AuthorizationService {
    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private GitHubAuthorizationApi gitHubAuthorizationApi;

    @Autowired
    private GitHubUserApi gitHubUserApi;

    public ResponseDTO callback(String code) {
        String accessToken = gitHubAuthorizationApi.getAccessToken(code);
        if (StringUtils.isNotBlank(accessToken)) {
            try {
                GitHubUser gitHubUser = gitHubUserApi.getByAccessToken(accessToken);
                if (gitHubUser != null) {
                    gitHubUser.setAccess_token(accessToken);
                    Authorization dbAuthorization = authorizationDao.get(gitHubUser.getId());
                    if (dbAuthorization != null) {
                        return ResponseDTO.ok(gitHubUser);
                    }
                    Authorization authorization = new Authorization();
                    authorization.setId(gitHubUser.getId());
                    authorization.setToken(accessToken);
                    authorization.setUtime(TimestampUtil.now());
                    authorization.setCtime(TimestampUtil.now());
                    authorizationDao.save(authorization);
                    return ResponseDTO.ok(gitHubUser);
                } else {
                    return ResponseDTO.error("Get GitHub account failed. Please retry or contact Administrator");
                }
            } catch (CommentHubException e) {
                return ResponseDTO.error("Get GitHub account failed. Please retry or contact Administrator.");
            }
        } else {
            return ResponseDTO.error("Authorization failed. Please retry or contact Administrator");
        }
    }

    public ResponseDTO save(Authorization authorization) {
        if (authorization.getCtime() == null) {
            authorization.setCtime(TimestampUtil.now());
            authorization.setUtime(TimestampUtil.now());
        }
        authorizationDao.save(authorization);
        return ResponseDTO.ok(authorization);
    }

    public ResponseDTO update(Authorization authorization) {
        authorization.setUtime(TimestampUtil.now());
        authorizationDao.update(authorization);
        return ResponseDTO.ok(authorization);
    }

    public void delete(Integer id) {
        authorizationDao.delete(id);
    }

    public ResponseDTO getByAccessToken(String accessToken) {
        try {
            GitHubUser gitHubUser = gitHubUserApi.getByAccessToken(accessToken);
            Authorization dbAuthorization = authorizationDao.get(gitHubUser.getId());
            if (dbAuthorization != null) {
                return ResponseDTO.ok(UserConverter.toDO(gitHubUser, dbAuthorization));
            }
            Authorization authorization = new Authorization();
            authorization.setId(gitHubUser.getId());
            authorization.setToken(accessToken);
            authorization.setUtime(TimestampUtil.now());
            authorization.setCtime(TimestampUtil.now());
            authorizationDao.save(authorization);
            return ResponseDTO.ok(UserConverter.toDO(gitHubUser, authorization));
        } catch (CommentHubException e) {
            return ResponseDTO.error("Get GitHub account failed. Please retry or contact Administrator");
        }
    }

    public ResponseDTO get(Integer id) {
        Authorization authorization = authorizationDao.get(id);
        try {
            GitHubUser gitHubUser = gitHubUserApi.getById(id);
            return ResponseDTO.ok(UserConverter.toDO(gitHubUser, authorization));
        } catch (CommentHubException e) {
            return ResponseDTO.error("Get GitHub account failed. Please retry or contact Administrator.");
        }
    }
}
