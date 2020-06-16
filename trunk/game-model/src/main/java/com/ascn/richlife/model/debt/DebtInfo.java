package com.ascn.richlife.model.debt;

/**
 * 负债信息
 */
public class DebtInfo {

    /**
     * 负债名称
     */
    private String debtName;

    /**
     * 负债金钱
     */
    private int debtMoney;

    /**
     * 负债利息
     */
    private int debtInterest;

    public String getDebtName() {
        return debtName;
    }

    public DebtInfo setDebtName(String debtName) {
        this.debtName = debtName;
        return this;
    }

    public int getDebtMoney() {
        return debtMoney;
    }

    public DebtInfo setDebtMoney(int debtMoney) {
        this.debtMoney = debtMoney;
        return this;
    }

    public int getDebtInterest() {
        return debtInterest;
    }

    public DebtInfo setDebtInterest(int debtInterest) {
        this.debtInterest = debtInterest;
        return this;
    }

    public DebtInfo(String debtName, int debtMoney, int debtInterest) {
        this.debtName = debtName;
        this.debtMoney = debtMoney;
        this.debtInterest = debtInterest;
    }

    public DebtInfo() {
    }
}
