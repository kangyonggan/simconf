package com.kangyonggan.app.simconf.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyg on 2017/2/16.
 */
public class SocketMap {

    private static Map<String, SocketThread> map;

    static {
        map = new HashMap();
    }

    public static void put(String key, SocketThread socketThread) {
        map.put(key, socketThread);
    }

    public static SocketThread get(String key) {
        return map.get(key);
    }

    public static void remove(String key) {
        map.remove(key);
    }

}
