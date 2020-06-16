package com.ascn.richlife.server.data;

import com.ascn.richlife.model.role.Role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 角色数据
 *
 * Created by zhangpengxiang on 2017/6/28.
 */
public class RoleData {

    public static Map<Integer, Role> roleData = new ConcurrentHashMap<>();

}
