package com.ascn.richlife.server.group;

import com.ascn.richlife.model.ChooseRoleData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 选择角色管理
 *
 * Created by zhangpengxiang on 2017/6/20.
 */
public class ChooseRoleManager {

    private static Map<String, ChooseRoleData> chooseRoleDataMap = new ConcurrentHashMap<>();

    public static void addData(String roomId, ChooseRoleData chooseRoleData) {
        chooseRoleDataMap.put(roomId, chooseRoleData);
    }

    public static ChooseRoleData getData(String roomId) {
        return chooseRoleDataMap.get(roomId);
    }

    public static void removeData(String roomId) {
        chooseRoleDataMap.remove(roomId);
    }



}
