package com.codedrinker.converter;

import com.codedrinker.dto.UserDTO;
import com.codedrinker.entity.Authorization;
import com.codedrinker.github.entity.GitHubUser;

/**
 * Created by codedrinker on 07/08/2017.
 */
public class UserConverter {
    public static UserDTO toDO(GitHubUser gitHubUser, Authorization authorization) {
        UserDTO userDTO = new UserDTO();
        if (gitHubUser != null) {
            userDTO.setName(gitHubUser.getName());
            userDTO.setLogin(gitHubUser.getLogin());
            userDTO.setId(gitHubUser.getId());
            userDTO.setAvatar_url(gitHubUser.getAvatar_url());
            userDTO.setEmail(gitHubUser.getEmail());
        }
        if (authorization != null) {
            userDTO.setWebsite(authorization.getWebsite());
        }
        return userDTO;
    }
}
