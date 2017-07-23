package com.codedrinker.utils;

/**
 * Created by codedrinker on 23/07/2017.
 */
public class LogUtils {
    public static void log(String msg, Object o) {
        System.out.print(msg + " : ");
        System.out.println(o);
    }

    public static void log(String msg, Object o, Throwable throwable) {
        System.out.print(msg + " : ");
        System.out.println(o);
        System.out.println(throwable.getMessage());
    }
}
