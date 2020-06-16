package com.ascn.richlife.service.role;


import com.ascn.richlife.model.income.NonLaborIncome;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleIncomeInfo;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 角色收入信息服务
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleIncomeInfoService {


    /**
     * 初始化角色收入
     *
     * @param role
     * @return
     */
    RoleIncomeInfo init(Role role);

    /**
     * 初始化内圈收入
     *
     * @param roleIncomeInfo
     */
    void innerInit(RoleIncomeInfo roleIncomeInfo);

    /**
     * 添加非劳务收入
     *
     * @param roleInfo
     * @param nonLaborIncome
     */
    void addNonLaborIncome(RoleInfo roleInfo, NonLaborIncome nonLaborIncome);

    /**
     * 删除非劳务收入
     *
     * @param roleInfo
     * @param nonLaborIncome
     */
    void removeNonLaborIncome(RoleInfo roleInfo, NonLaborIncome nonLaborIncome);

}
