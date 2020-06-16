package com.ascn.richlife.model.role;

import com.ascn.richlife.model.debt.DebtInfo;

import java.util.Map;

/**
 * 角色贷款信息
 */
public class RoleDebtsInfo {

    /**
     * 基础负债
     */
    private Map<String,DebtInfo> basicDebt;

    /**
     * 新增负债
     */
    private Map<String,DebtInfo> addNewDebt;

    public Map<String, DebtInfo> getBasicDebt() {
        return basicDebt;
    }

    public RoleDebtsInfo setBasicDebt(Map<String, DebtInfo> basicDebt) {
        this.basicDebt = basicDebt;
        return this;
    }

    public Map<String, DebtInfo> getAddNewDebt() {
        return addNewDebt;
    }

    public RoleDebtsInfo setAddNewDebt(Map<String, DebtInfo> addNewDebt) {
        this.addNewDebt = addNewDebt;
        return this;
    }
}
