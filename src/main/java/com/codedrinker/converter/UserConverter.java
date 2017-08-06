package com.codedrinker.converter;

import com.codedrinker.dto.UserDTO;
import com.codedrinker.github.entity.GitHubUser;

/**
 * Created by codedrinker on 06/08/2017.
 */
public class UserConverter {
    public static UserDTO toDO(GitHubUser gitHubUser) {
        if (gitHubUser != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(gitHubUser.getId());
            userDTO.setAvatar(gitHubUser.getAvatar_url());
            userDTO.setLogin(gitHubUser.getLogin());
            userDTO.setName(gitHubUser.getName());
            return userDTO;
        } else {
            return null;
        }
    }
}
