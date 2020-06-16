package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleSpendInfo;
import com.ascn.richlife.model.spend.SpendInfo;

/**
 * 角色话费支出信息服务
 *
 * Created by zhangpengxiang on 17/4/22.
 */
public interface RoleSpendInfoService {

    /**
     * 初始化支出信息
     *
     * @param role
     * @return
     */
    RoleSpendInfo init(Role role);

    /**
     * 添加新增支出
     *
     * @param roleInfo
     * @param spendInfo
     */
    void addNewSpend(RoleInfo roleInfo, SpendInfo spendInfo);

    /**
     * 添加基础支出
     *
     * @param roleInfo
     * @param spendInfo
     */
    void addBasicSpend(RoleInfo roleInfo, SpendInfo spendInfo);

    /**
     * 删除基础支出
     *
     * @param roleInfo
     * @param spendName
     */
    void removeBasicSpend(RoleInfo roleInfo, String spendName);

    /**
     * 删除新增的支出
     *
     * @param roleInfo
     * @param spendName
     */
    void removeNewSpend(RoleInfo roleInfo, String spendName);
}
