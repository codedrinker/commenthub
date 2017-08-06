package com.codedrinker.github;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by codedrinker on 06/08/2017.
 */
@Component
public class GitHubAuthorizationApi {
    private final Logger logger = LoggerFactory.getLogger(GitHubAuthorizationApi.class);
    private static String authorization_url = "https://github.com/login/oauth/access_token";

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    public String getAccessToken(String code) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("client_id", clientId);
            jsonObject.put("client_secret", clientSecret);
            jsonObject.put("code", code);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toJSONString());
            Request request = new Request.Builder()
                    .url(authorization_url)
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            if (StringUtils.isNotBlank(string) && StringUtils.contains(string, "access_token")) {
                String accessToken = StringUtils.split(StringUtils.split(string, "&")[0], "=")[1];
                return accessToken;
            } else {
                logger.error("Exchange Access Token error {}", string);
                return null;
            }
        } catch (IOException e) {
            logger.error("Exchange Access Token error", e);
            return null;
        }
    }
}
