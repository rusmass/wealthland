package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleDataManageInfo;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 角色信息管理服务
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleDataManageInfoService {

    /**
     * 初始化角色数据信息
     *
     * @param role
     * @return
     */
    RoleDataManageInfo init(Role role);

    /**
     * 内圈初始化
     *
     * @param roleDataManageInfo
     */
    void innerInit(RoleDataManageInfo roleDataManageInfo);

    /**
     * 更新角色现金
     *
     * @param roleDataManageInfo
     * @param money
     */
    void updateRoleCash(RoleDataManageInfo roleDataManageInfo, int money);

    /**
     * 更新角色资产总额
     *
     * @param roleDataManageInfo
     * @param money
     */
    void updateRoleAssetTotalMoney(RoleDataManageInfo roleDataManageInfo, int money);

    /**
     * 更新角色负债总额
     *
     * @param roleDataManageInfo
     * @param money
     */
    void updateRoleDeptTotalMoney(RoleDataManageInfo roleDataManageInfo, int money);

    /**
     * 更新角色总收入
     *
     * @param roleInfo
     * @param money
     */
    void updateRoleTotalIncome(RoleInfo roleInfo, int money);

    /**
     * 更新角色总支出
     *
     * @param roleInfo
     * @param money
     */
    void updateRoleTotalSpending(RoleInfo roleInfo, int money);

    /**
     * 更新角色结账日金额
     *
     * @param roleInfo
     * @param money
     */
    void updateRoleClosingDateMoney(RoleInfo roleInfo, int money);

    /**
     * 更新角色非劳务收入
     *
     * @param roleDataManageInfo
     * @param money
     */
    void updateRoleNonLaborIncome(RoleDataManageInfo roleDataManageInfo, int money);

    /**
     * 更新角色流动现金
     *
     * @param roleInfo
     * @param money
     */
    void updateRoleFlowCash(RoleInfo roleInfo, int money);

    /**
     * 更新角色品质积分
     *
     * @param roleDataManageInfo
     * @param integral
     */
    void updateRoleQualityIntegral(RoleDataManageInfo roleDataManageInfo, int integral);

    /**
     * 更新角色时间积分
     *
     * @param roleDataManageInfo
     * @param integral
     */
    void updateRoleTimeIntegral(RoleDataManageInfo roleDataManageInfo, int integral);

    /**
     * 更新孩子数量
     *
     * @param roleInfo
     */
    void updateRoleHaveChildNumber(RoleInfo roleInfo);
}
