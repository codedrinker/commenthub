package com.codedrinker.utils;

/**
 * Created by codedrinker on 06/08/2017.
 */
public class TimstampUtil {
    public static int now() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}
