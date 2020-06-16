package com.ascn.richlife.model.login;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存当前账户组
 */
public class Group {

    /**
     * 保存当前账户组
     */
    public static Map<String,String> accountGroup = new ConcurrentHashMap<String, String>();

}
