package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 初始化角色信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleInfoInitService {

    /**
     * 加载角色信息
     * @param roleId
     * @return
     */
    Role loadRoleInitData(int roleId) throws Exception;

    /**
     * 初始化角色的所有信息
     * @return
     */
    RoleInfo roleInit(Role role)throws Exception;


}
