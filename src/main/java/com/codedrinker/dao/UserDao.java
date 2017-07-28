package com.codedrinker.dao;

import com.codedrinker.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * Created by codedrinker on 07/07/2017.
 */
public interface UserDao {
    @Insert("insert into user (username,nickname,email,website) values (#{username},#{nickname},#{email},#{website});")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void save(User user);
}
