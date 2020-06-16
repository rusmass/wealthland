package com.ascn.richlife.model.role;

/**
 * 角色模型信息管理
 */
public class RoleDataManageInfo {

    /**
     * 年龄
     */
    private int age;

    /**
     * 现金
     */
    private int cash;

    /**
     * 资产总额
     */
    private int assetTotalMoney;

    /**
     * 负债总额
     */
    private int debtTotalMoney;

    /**
     * 总支出
     */
    private int totalSpending;

    /**
     * 总收入
     */
    private int totalIncome;

    /**
     * 结账日金额
     */
    private int closingDateMoney;

    /**
     * 非劳务收入
     */
    private int nonLaborIncome;

    /**
     * 内圈已经获得的流动现金
     */
    private int cashFlow;

    /**
     * 初始流动现金
     */
    private int initCashFlow;

    /**
     * 品质积分
     */
    private int qualityIntegral;

    /**
     * 时间积分
     */
    private int timeIntegral;

    /**
     * 拥有的孩子数量
     */
    private int haveChildNumber;

    public int getAge() {
        return age;
    }

    public RoleDataManageInfo setAge(int age) {
        this.age = age;
        return this;
    }

    public int getCash() {
        return cash;
    }

    public RoleDataManageInfo setCash(int cash) {
        this.cash = cash;
        return this;
    }

    public int getAssetTotalMoney() {
        return assetTotalMoney;
    }

    public RoleDataManageInfo setAssetTotalMoney(int assetTotalMoney) {
        this.assetTotalMoney = assetTotalMoney;
        return this;
    }

    public int getDebtTotalMoney() {
        return debtTotalMoney;
    }

    public RoleDataManageInfo setDebtTotalMoney(int debtTotalMoney) {
        this.debtTotalMoney = debtTotalMoney;
        return this;
    }

    public int getTotalSpending() {
        return totalSpending;
    }

    public RoleDataManageInfo setTotalSpending(int totalSpending) {
        this.totalSpending = totalSpending;
        return this;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public RoleDataManageInfo setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
        return this;
    }

    public int getClosingDateMoney() {
        return closingDateMoney;
    }

    public RoleDataManageInfo setClosingDateMoney(int closingDateMoney) {
        this.closingDateMoney = closingDateMoney;
        return this;
    }

    public int getNonLaborIncome() {
        return nonLaborIncome;
    }

    public RoleDataManageInfo setNonLaborIncome(int nonLaborIncome) {
        this.nonLaborIncome = nonLaborIncome;
        return this;
    }

    public int getCashFlow() {
        return cashFlow;
    }

    public RoleDataManageInfo setCashFlow(int cashFlow) {
        this.cashFlow = cashFlow;
        return this;
    }

    public int getInitCashFlow() {
        return initCashFlow;
    }

    public RoleDataManageInfo setInitCashFlow(int initCashFlow) {
        this.initCashFlow = initCashFlow;
        return this;
    }

    public int getQualityIntegral() {
        return qualityIntegral;
    }

    public RoleDataManageInfo setQualityIntegral(int qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
        return this;
    }

    public int getTimeIntegral() {
        return timeIntegral;
    }

    public RoleDataManageInfo setTimeIntegral(int timeIntegral) {
        this.timeIntegral = timeIntegral;
        return this;
    }

    public int getHaveChildNumber() {
        return haveChildNumber;
    }

    public RoleDataManageInfo setHaveChildNumber(int haveChildNumber) {
        this.haveChildNumber = haveChildNumber;
        return this;
    }
}
