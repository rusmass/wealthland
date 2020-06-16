package com.ascn.richlife.service.role.impl;

import com.ascn.richlife.dao.role.RoleDao;
import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;
import com.ascn.richlife.model.role.*;
import com.ascn.richlife.service.role.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化角色信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Component("roleInfoInitService")
public class RoleInfoInitServiceImpl implements RoleInfoInitService {

    private Logger logger = Logger.getLogger(RoleInfoInitServiceImpl.class);

    //角色数据接口
    @Autowired
    private RoleDao roleDao;

    //角色基本信息接口
    @Autowired
    private RoleBasicInfoService roleBasicInfoService;

    //角色模型信息接口
    @Autowired
    private RoleModelInfoService roleModelInfoService;

    //角色拥有资产信息接口
    @Autowired
    private RoleHaveAssetInfoService roleHaveAssetInfoService;

    //角色出售资产信息接口
    @Autowired
    private RoleSellAssetRecordService roleSellAssetRecordService;

    //角色收入信息接口
    @Autowired
    private RoleIncomeInfoService roleIncomeInfoService;

    //角色积分信息接口
    @Autowired
    private RoleDataManageInfoService roleDataManageInfoService;

    //角色负债信息接口
    @Autowired
    private RoleDebtsInfoService roleDebtsInfoService;

    //角色支出信息接口
    @Autowired
    private RoleSpendInfoService roleSpendInfoService;

    //银行贷款信息接口
    @Autowired
    private RoleLoanInfoService roleLoanInfoService;

    //借款信息
    @Autowired
    private RoleLoanRecordInfoService roleLoanRecordInfoService;

    //积分记录
    @Autowired
    private RoleIntegralRecordService roleIntegralRecordService;

    public Role loadRoleInitData(int roleId) throws Exception {
        return roleDao.getRoleInfo(roleId);
    }

    public RoleInfo roleInit(Role role) {

        RoleInfo roleInfo = new RoleInfo();

        logger.debug("开始初始化角色模型信息...");
        RoleModelInfo roleModelInfo = roleModelInfoService.init(role);

        logger.debug("开始初始化角色基本信息...");
        RoleBasicInfo roleBasicInfo = roleBasicInfoService.init(role);

        logger.debug("开始初始化角色数据信息...");
        RoleDataManageInfo roleDataManageInfo = roleDataManageInfoService.init(role);

        logger.debug("开始初始化角色拥有的资产信息...");
        RoleHaveAssetInfo roleHaveAssetInfo = roleHaveAssetInfoService.init(role);

        logger.debug("开始初始化角色出售记录...");
        RoleSellAssetRecord roleSellAssetRecord = roleSellAssetRecordService.init(role);

        logger.debug("开始初始化角色收入信息...");
        RoleIncomeInfo roleIncomeInfo = roleIncomeInfoService.init(role);

        logger.debug("开始初始化角色负债信息...");
        RoleDebtsInfo roleDebtsInfo = roleDebtsInfoService.init(role);

        logger.debug("开始初始化角色支出信息...");
        RoleSpendInfo roleSpendInfo = roleSpendInfoService.init(role);

        logger.debug("开始初始化角色银行贷款信息...");
        RoleLoanInfo roleLoanInfo = roleLoanInfoService.init(roleDataManageInfo);

        logger.debug("开始初始化角色贷款记录");
        RoleLoanRecordInfo roleLoanRecordInfo = roleLoanRecordInfoService.init(roleLoanInfo);

        //初始化角色积分记录
        RoleIntegralRecord roleIntegralRecord = roleIntegralRecordService.init();

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
