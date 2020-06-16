package com.ascn.richlife.model.role;

import com.ascn.richlife.model.spend.SpendInfo;

import java.util.Map;

/**
 * 角色支出信息
 */
public class RoleSpendInfo {

    /**
     * 基础支出
     */
    private Map<String,SpendInfo> basicSpend;

    /**
     * 新增支出
     */
    private Map<String,SpendInfo> addNewSpend;

    public Map<String, SpendInfo> getBasicSpend() {
        return basicSpend;
    }

    public RoleSpendInfo setBasicSpend(Map<String, SpendInfo> basicSpend) {
        this.basicSpend = basicSpend;
        return this;
    }

    public Map<String, SpendInfo> getAddNewSpend() {
        return addNewSpend;
    }

    public RoleSpendInfo setAddNewSpend(Map<String, SpendInfo> addNewSpend) {
        this.addNewSpend = addNewSpend;
        return this;
    }
}
