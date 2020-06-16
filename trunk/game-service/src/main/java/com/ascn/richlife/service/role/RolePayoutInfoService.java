package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RolePayoutInfo;

/**
 * 角色支出信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RolePayoutInfoService {
    /**
     *
     * @param role
     * @return
     */
    RolePayoutInfo init(Role role);
}
