package com.codedrinker.dao;

import com.codedrinker.db.DBDataSource;
import com.codedrinker.entity.Comment;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Repository
public class CommentDao {

    @Autowired
    private DBDataSource dbDataSource;

    public void save(Comment comment) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "insert into comment (id,kind,title,description,date,time,location,user_id,utime,ctime) values (?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            String id = UUID.randomUUID().toString();
            pstmt.setString(1, comment.getId());
            pstmt.setLong(10, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DBException(e.getMessage());
        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
