package com.codedrinker.service;

import com.codedrinker.dto.CommentDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.github.GitHubCommentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by codedrinker on 14/09/2017.
 */
@Service
public class CommentService {

    @Autowired
    private GitHubCommentApi gitHubCommentApi;

    public ResponseDTO createComment(String accessToke, CommentDTO commentDTO) {
        try {
            String comment = gitHubCommentApi.create(accessToke, commentDTO.getCommentsUrl(), commentDTO.getBody());
            return ResponseDTO.ok(comment);
        } catch (CommentHubException e) {
            return ResponseDTO.error(e.getMessage());
        }
    }
}
