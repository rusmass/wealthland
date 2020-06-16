package com.ascn.richlife.model.role;

import java.util.Map;

/**
 * 角色支出信息
 */
public class RolePayoutInfo {

    /**
     * 基础支出
     */
    private Map<String,Integer> basicPayout;

    /**
     * 新增支出
     */
    private Map<String,Integer> addNewPayout;

    public Map<String, Integer> getBasicPayout() {
        return basicPayout;
    }

    public RolePayoutInfo setBasicPayout(Map<String, Integer> basicPayout) {
        this.basicPayout = basicPayout;
        return this;
    }

    public Map<String, Integer> getAddNewPayout() {
        return addNewPayout;
    }

    public RolePayoutInfo setAddNewPayout(Map<String, Integer> addNewPayout) {
        this.addNewPayout = addNewPayout;
        return this;
    }
}
