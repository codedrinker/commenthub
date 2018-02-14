package com.codedrinker.service;

import com.codedrinker.dto.IssueDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.github.GitHubIssueApi;
import com.codedrinker.github.entity.GitHubIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 14/09/2017.
 */
@Service
public class IssueService {
    @Value("${github.user}")
    private String user;

    @Value("${github.repo}")
    private String repo;

    @Value("${github.token}")
    private String token;

    @Autowired
    private GitHubIssueApi gitHubIssueApi;

    private final Logger logger = LoggerFactory.getLogger(IssueService.class);

    public ResponseDTO createIssue(String accessToken, IssueDTO issueDTO) {
        try {
            GitHubIssue issue = new GitHubIssue();
            issue.setBody(issueDTO.getUrl());
            issue.setOwner(user);
            issue.setRepo(repo);
            issue.setTitle(issueDTO.getTitle());
            GitHubIssue createdIssue = gitHubIssueApi.create(accessToken, issue);
            if (createdIssue != null) {
                logger.info("create issue success");
                List<String> labels = new ArrayList<>();
                labels.add(issueDTO.getWebsite());
                labels.add(issueDTO.getIdentifier());
                gitHubIssueApi.addLabels2Issue(token, createdIssue, labels);
                return ResponseDTO.ok(createdIssue);
            } else {
                return ResponseDTO.error("Create error, please retry.");
            }
        } catch (CommentHubException e) {
            return ResponseDTO.error(e.getMessage());
        }
    }
}
