package com.ascn.richlife.model.role;

import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;

/**
 * 角色信息
 */
public class RoleInfo {

    /**
     * 角色基本信息
     */
    private RoleBasicInfo roleBasicInfo;

    /**
     * 角色模型信息
     */
    private RoleModelInfo roleModelInfo;

    /**
     * 角色数据信息
     */
    private RoleDataManageInfo roleDataManageInfo;

    /**
     * 角色拥有资产信息
     */
    private RoleHaveAssetInfo roleHaveAssetInfo;

    /**
     * 角色出售资产记录
     */
    private RoleSellAssetRecord roleSellAssetRecord;

    /**
     * 角色收入信息
     */
    private RoleIncomeInfo roleIncomeInfo;

    /**
     * 角色负债信息
     */
    private RoleDebtsInfo roleDebtsInfo;

    /**
     * 角色支出信息
     */
    private RoleSpendInfo roleSpendInfo;

    /**
     * 角色银行贷款信息
     */
    private RoleLoanInfo roleLoanInfo;

    /**
     * 角色贷款记录
     */
    private RoleLoanRecordInfo roleLoanRecordInfo;

    /**
     * 积分记录
     */
    private RoleIntegralRecord roleIntegralRecord;

    public RoleBasicInfo getRoleBasicInfo() {
        return roleBasicInfo;
    }

    public void setRoleBasicInfo(RoleBasicInfo roleBasicInfo) {
        this.roleBasicInfo = roleBasicInfo;
    }

    public RoleModelInfo getRoleModelInfo() {
        return roleModelInfo;
    }

    public void setRoleModelInfo(RoleModelInfo roleModelInfo) {
        this.roleModelInfo = roleModelInfo;
    }

    public RoleDataManageInfo getRoleDataManageInfo() {
        return roleDataManageInfo;
    }

    public void setRoleDataManageInfo(RoleDataManageInfo roleDataManageInfo) {
        this.roleDataManageInfo = roleDataManageInfo;
    }

    public RoleHaveAssetInfo getRoleHaveAssetInfo() {
        return roleHaveAssetInfo;
    }

    public void setRoleHaveAssetInfo(RoleHaveAssetInfo roleHaveAssetInfo) {
        this.roleHaveAssetInfo = roleHaveAssetInfo;
    }

    public RoleSellAssetRecord getRoleSellAssetRecord() {
        return roleSellAssetRecord;
    }

    public void setRoleSellAssetRecord(RoleSellAssetRecord roleSellAssetRecord) {
        this.roleSellAssetRecord = roleSellAssetRecord;
    }

    public RoleIncomeInfo getRoleIncomeInfo() {
        return roleIncomeInfo;
    }

    public void setRoleIncomeInfo(RoleIncomeInfo roleIncomeInfo) {
        this.roleIncomeInfo = roleIncomeInfo;
    }

    public RoleDebtsInfo getRoleDebtsInfo() {
        return roleDebtsInfo;
    }

    public void setRoleDebtsInfo(RoleDebtsInfo roleDebtsInfo) {
        this.roleDebtsInfo = roleDebtsInfo;
    }

    public RoleSpendInfo getRoleSpendInfo() {
        return roleSpendInfo;
    }

    public void setRoleSpendInfo(RoleSpendInfo roleSpendInfo) {
        this.roleSpendInfo = roleSpendInfo;
    }

    public RoleLoanInfo getRoleLoanInfo() {
        return roleLoanInfo;
    }

    public void setRoleLoanInfo(RoleLoanInfo roleLoanInfo) {
        this.roleLoanInfo = roleLoanInfo;
    }

    public RoleLoanRecordInfo getRoleLoanRecordInfo() {
        return roleLoanRecordInfo;
    }

    public void setRoleLoanRecordInfo(RoleLoanRecordInfo roleLoanRecordInfo) {
        this.roleLoanRecordInfo = roleLoanRecordInfo;
    }

    public RoleIntegralRecord getRoleIntegralRecord() {
        return roleIntegralRecord;
    }

    public void setRoleIntegralRecord(RoleIntegralRecord roleIntegralRecord) {
        this.roleIntegralRecord = roleIntegralRecord;
    }
}
