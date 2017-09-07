package com.codedrinker.github;

import okhttp3.OkHttpClient;

/**
 * Created by codedrinker on 06/09/2017.
 */
public class HttpClient {
    private volatile static OkHttpClient instance; //声明成 volatile

    private HttpClient() {
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new OkHttpClient();
                }
            }
        }
        return instance;
    }

}
