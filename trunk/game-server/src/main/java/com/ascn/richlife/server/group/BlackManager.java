package com.ascn.richlife.server.group;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单管理
 *
 * Created by zhangpengxiang on 2017/6/9.
 */
public class BlackManager {

    private static List<String> blackList = new ArrayList<>();

    /**
     * 验证IP
     *
     * @param ip
     * @return
     */
    public static boolean verify(String ip) {
        return blackList.contains(ip);
    }
}
