package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleBasicInfo;

/**
 * 角色基本信息服务
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleBasicInfoService {
    /**
     * 初始化角色基本信息
     * @param role
     * @return
     */
    RoleBasicInfo init(Role role);

}
