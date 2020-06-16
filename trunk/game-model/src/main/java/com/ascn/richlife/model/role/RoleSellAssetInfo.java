package com.ascn.richlife.model.role;

/**
 * 资产信息
 */
public class RoleSellAssetInfo {

    /**
     * 资产名字
     */
    private String name;

    /**
     * 单价
     */
    private int price;

    /**
     * 数量
     */
    private int number;

    /**
     * 抵押贷款
     */
    private int loan;

    /**
     * 卖价
     */
    private int sellPrice;

    /**
     * 非劳务收入
     */
    private int nonLaborIncome;

    /**
     * 品质积分
     */
    private int qualityIntegral;

    /**
     * 净赚
     */
    private int net;

    public String getName() {
        return name;
    }

    public RoleSellAssetInfo setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public RoleSellAssetInfo setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public RoleSellAssetInfo setNumber(int number) {
        this.number = number;
        return this;
    }

    public int getLoan() {
        return loan;
    }

    public RoleSellAssetInfo setLoan(int loan) {
        this.loan = loan;
        return this;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public RoleSellAssetInfo setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public int getNonLaborIncome() {
        return nonLaborIncome;
    }

    public RoleSellAssetInfo setNonLaborIncome(int nonLaborIncome) {
        this.nonLaborIncome = nonLaborIncome;
        return this;
    }

    public int getQualityIntegral() {
        return qualityIntegral;
    }

    public RoleSellAssetInfo setQualityIntegral(int qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
        return this;
    }

    public int getNet() {
        return net;
    }

    public RoleSellAssetInfo setNet(int net) {
        this.net = net;
        return this;
    }
}
