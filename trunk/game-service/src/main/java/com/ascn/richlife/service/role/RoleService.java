package com.ascn.richlife.service.role;

import com.ascn.richlife.model.role.Role;

/**
 * 查询角色信息
 *
 * Created by zhangpengxiang on 2017/6/28.
 */
public interface RoleService {

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    Role getRoleInfo(int id);

}
