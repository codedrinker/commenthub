package com.codedrinker.service;

import com.codedrinker.dao.UserDao;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 23/07/2017.
 */
@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public ResponseDTO save(User user) {
        userDao.save(user);
        return ResponseDTO.ok(user);
    }
}
