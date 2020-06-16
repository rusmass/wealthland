package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;
import com.ascn.richlife.model.role.*;
import com.ascn.richlife.service.role.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 角色初始化管理
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Component("roleInitHandler")
public class RoleInitHandler {

    private Logger logger = Logger.getLogger(RoleInitHandler.class);

    //角色数据接口
    @Autowired
    private RoleService roleService;

    //角色基本信息接口
    @Autowired
    private RoleBasicInfoHandler roleBasicInfoHandler;

    //角色模型信息接口
    @Autowired
    private RoleModelInfoHandler roleModelInfoHandler;

    //角色拥有资产信息接口
    @Autowired
    private RoleHaveAssetInfoHandler roleHaveAssetInfoHandler;

    //角色出售资产信息接口
    @Autowired
    private RoleSellAssetRecordHandler roleSellAssetRecordHandler;

    //角色收入信息接口
    @Autowired
    private RoleIncomeInfoHandler roleIncomeInfoHandler;

    //角色积分信息接口
    @Autowired
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    //角色负债信息接口
    @Autowired
    private RoleDebtsInfoHandler roleDebtsInfoHandler;

    //角色支出信息接口
    @Autowired
    private RoleSpendInfoHandler roleSpendInfoHandler;

    //银行贷款信息接口
    @Autowired
    private RoleLoanInfoHandler roleLoanInfoHandler;

    //借款信息
    @Autowired
    private RoleLoanRecordInfoHandler roleLoanRecordInfoHandler;

    //积分记录
    @Autowired
    private RoleIntegralRecordHandler roleIntegralRecordHandler;

    public RoleInfo roleInit(int roleId) {

        Role role = roleService.getRoleInfo(roleId);

        RoleInfo roleInfo = new RoleInfo();

        logger.debug("开始初始化角色模型信息...");
        RoleModelInfo roleModelInfo = roleModelInfoHandler.init(role);

        logger.debug("开始初始化角色基本信息...");
        RoleBasicInfo roleBasicInfo = roleBasicInfoHandler.init(role);

        logger.debug("开始初始化角色数据信息...");
        RoleDataManageInfo roleDataManageInfo = roleDataManageInfoHandler.init(role);

        logger.debug("开始初始化角色拥有的资产信息...");
        RoleHaveAssetInfo roleHaveAssetInfo = roleHaveAssetInfoHandler.init(role);

        logger.debug("开始初始化角色出售记录...");
        RoleSellAssetRecord roleSellAssetRecord = roleSellAssetRecordHandler.init(role);

        logger.debug("开始初始化角色收入信息...");
        RoleIncomeInfo roleIncomeInfo = roleIncomeInfoHandler.init(role);

        logger.debug("开始初始化角色负债信息...");
        RoleDebtsInfo roleDebtsInfo = roleDebtsInfoHandler.init(role);

        logger.debug("开始初始化角色支出信息...");
        RoleSpendInfo roleSpendInfo = roleSpendInfoHandler.init(role);

        logger.debug("开始初始化角色银行贷款信息...");
        RoleLoanInfo roleLoanInfo = roleLoanInfoHandler.init(roleDataManageInfo);

        logger.debug("开始初始化角色贷款记录");
        RoleLoanRecordInfo roleLoanRecordInfo = roleLoanRecordInfoHandler.init(roleLoanInfo);

        logger.debug("开始初始化角色积分记录");
        RoleIntegralRecord roleIntegralRecord = roleIntegralRecordHandler.init();

        roleInfo.setRoleBasicInfo(roleBasicInfo);

        roleInfo.setRoleModelInfo(roleModelInfo);

        roleInfo.setRoleDataManageInfo(roleDataManageInfo);

        roleInfo.setRoleHaveAssetInfo(roleHaveAssetInfo);

        roleInfo.setRoleSellAssetRecord(roleSellAssetRecord);

        roleInfo.setRoleIncomeInfo(roleIncomeInfo);

        roleInfo.setRoleDebtsInfo(roleDebtsInfo);

        roleInfo.setRoleSpendInfo(roleSpendInfo);

        roleInfo.setRoleLoanInfo(roleLoanInfo);

        roleInfo.setRoleLoanRecordInfo(roleLoanRecordInfo);

        roleInfo.setRoleIntegralRecord(roleIntegralRecord);

        return roleInfo;
    }
}
