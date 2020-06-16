package com.ascn.richlife.service.role;


import com.ascn.richlife.model.debt.DebtInfo;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleDebtsInfo;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 角色负债信息服务
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleDebtsInfoService {

    /**
     * 负债
     *
     * @param role
     * @return
     */
    RoleDebtsInfo init(Role role);

    /**
     * 初始化内圈负债
     *
     * @param roleDebtsInfo
     */
    void innerInit(RoleDebtsInfo roleDebtsInfo);

    /**
     * 新增负债
     *
     * @param roleInfo
     * @param debtInfo
     */
    void addNewDebt(RoleInfo roleInfo, DebtInfo debtInfo);

    /**
     * 移除基础负债
     *
     * @param roleDebtsInfo
     * @param debtName
     */
    void removeBasicDebt(RoleDebtsInfo roleDebtsInfo, String debtName);

    /**
     * 移除新增负债
     *
     * @param roleDebtsInfo
     * @param debtName
     */
    void removeNewDebt(RoleDebtsInfo roleDebtsInfo, String debtName);

}
