package com.codedrinker.github;

import com.alibaba.fastjson.JSONObject;
import com.codedrinker.exception.CommentHubException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by codedrinker on 06/08/2017.
 */
@Component
public class GitHubCommentApi extends AbstractGitHubApi {
    private final Logger logger = LoggerFactory.getLogger(GitHubCommentApi.class);

    public String create(String accessToken, String commentUrl, String body) throws CommentHubException {
        try {
            String url = commentUrl + "?access_token=" + accessToken;
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body", body);
            Request request = new Request.Builder()
                    .addHeader("Accept", "application/vnd.github.squirrel-girl-preview, application/vnd.github.html+json")
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toJSONString()))
                    .build();

            Response execute = client.newCall(request).execute();
            if (execute.isSuccessful()) {
                return execute.body().string();
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("get user by access token error -> {}", accessToken, e);
            throw new CommentHubException(e.getMessage());
        }
    }
}
