package com.ascn.richlife.service.role;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleModelInfo;

/**
 * 初始化模型信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleModelInfoService {
    /**
     * 初始化模型信息
     * @param role
     * @return
     */
    RoleModelInfo init(Role role);
}
