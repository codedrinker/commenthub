package com.codedrinker.github;

import com.alibaba.fastjson.JSON;
import com.codedrinker.exception.CommentHubException;
import com.codedrinker.github.entity.GitHubUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by codedrinker on 06/08/2017.
 */
@Component
public class GitHubUserApi extends AbstractGitHubApi {
    private final Logger logger = LoggerFactory.getLogger(GitHubUserApi.class);

    public GitHubUser getByAccessToken(String accessToken) throws CommentHubException {
        try {
            String url = BASE_URL + "/user?access_token=" + accessToken;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            GitHubUser gitHubUser = JSON.parseObject(response.body().string(), GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            logger.error("get user by access token error -> {}", accessToken, e);
            throw new CommentHubException(e.getMessage());
        }
    }

    public GitHubUser getById(Integer id) throws CommentHubException {
        try {
            String url = BASE_URL + "/user/" + id;
            OkHttpClient client = HttpClient.getInstance();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            GitHubUser gitHubUser = JSON.parseObject(response.body().string(), GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            logger.error("get user by access token error -> {}", id, e);
            throw new CommentHubException(e.getMessage());
        }
    }
}
