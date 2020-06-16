package com.ascn.richlife.model.role;

import com.ascn.richlife.model.income.LaborIncome;
import com.ascn.richlife.model.income.NonLaborIncome;

import java.util.List;

/**
 * 角色收入信息
 */
public class RoleIncomeInfo {

    /**
     * 角色劳务收入
     */
    private LaborIncome laborIncome;

    /**
     * 角色非劳务收入
     */
    private List<NonLaborIncome> nonLaborIncomeList;

    /**
     * 总收入
     */
    private int totalIncome;

    /**
     * 非劳务总收入
     */
    private int totalNonLaborIncome;

    public LaborIncome getLaborIncome() {
        return laborIncome;
    }

    public RoleIncomeInfo setLaborIncome(LaborIncome laborIncome) {
        this.laborIncome = laborIncome;
        return this;
    }

    public List<NonLaborIncome> getNonLaborIncomeList() {
        return nonLaborIncomeList;
    }

    public RoleIncomeInfo setNonLaborIncomeList(List<NonLaborIncome> nonLaborIncomeList) {
        this.nonLaborIncomeList = nonLaborIncomeList;
        return this;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public RoleIncomeInfo setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
        return this;
    }

    public int getTotalNonLaborIncome() {
        return totalNonLaborIncome;
    }

    public RoleIncomeInfo setTotalNonLaborIncome(int totalNonLaborIncome) {
        this.totalNonLaborIncome = totalNonLaborIncome;
        return this;
    }
}
