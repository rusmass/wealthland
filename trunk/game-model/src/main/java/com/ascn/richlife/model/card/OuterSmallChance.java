package com.ascn.richlife.model.card;

/**
 * 小机会资产
 */
public class OuterSmallChance {

    /**
     * id
     */
    private int id;

    /**
     * 所属类型
     */
    private int type;

    /**
     * 资产名称
     */
    private String name;

    /**
     * 卡牌路径
     */
    private String path;

    /**
     * 描述
     */
    private String instructions;

    /**
     * 数量
     */
    private int number;

    /**
     * 成本
     */
    private String cost;

    /**
     * 售价
     */
    private String sellPrice;

    /**
     * 首付
     */
    private int downPayment;

    /**
     * 投资收益
     */
    private String investmentIncome;

    /**
     * 抵押贷款
     */
    private int mortgageLoan;

    /**
     * 积分类型
     */
    private int integralType;

    /**
     * 积分数量
     */
    private int integralNumber;

    /**
     * 非劳务收入
     */
    private int nonLaborIncome;

    /**
     * 卡牌积分
     */
    private int cardIntegral;

    /**
     * 放弃积分
     */
    private int giveUpIntegral;

    public int getId() {
        return id;
    }

    public OuterSmallChance setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public OuterSmallChance setType(int type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public OuterSmallChance setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public OuterSmallChance setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public OuterSmallChance setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public OuterSmallChance setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getCost() {
        return cost;
    }

    public OuterSmallChance setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public OuterSmallChance setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public OuterSmallChance setDownPayment(int downPayment) {
        this.downPayment = downPayment;
        return this;
    }

    public String getInvestmentIncome() {
        return investmentIncome;
    }

    public OuterSmallChance setInvestmentIncome(String investmentIncome) {
        this.investmentIncome = investmentIncome;
        return this;
    }

    public int getMortgageLoan() {
        return mortgageLoan;
    }

    public OuterSmallChance setMortgageLoan(int mortgageLoan) {
        this.mortgageLoan = mortgageLoan;
        return this;
    }

    public int getIntegralType() {
        return integralType;
    }

    public OuterSmallChance setIntegralType(int integralType) {
        this.integralType = integralType;
        return this;
    }

    public int getIntegralNumber() {
        return integralNumber;
    }

    public OuterSmallChance setIntegralNumber(int integralNumber) {
        this.integralNumber = integralNumber;
        return this;
    }

    public int getNonLaborIncome() {
        return nonLaborIncome;
    }

    public OuterSmallChance setNonLaborIncome(int nonLaborIncome) {
        this.nonLaborIncome = nonLaborIncome;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public OuterSmallChance setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
        return this;
    }

    public int getGiveUpIntegral() {
        return giveUpIntegral;
    }

    public void setGiveUpIntegral(int giveUpIntegral) {
        this.giveUpIntegral = giveUpIntegral;
    }

    @Override
    public String toString() {
        return "OuterSmallChance{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", number=" + number +
                ", cost='" + cost + '\'' +
                ", sellPrice='" + sellPrice + '\'' +
                ", downPayment=" + downPayment +
                ", investmentIncome='" + investmentIncome + '\'' +
                ", mortgageLoan=" + mortgageLoan +
                ", integralType=" + integralType +
                ", integralNumber=" + integralNumber +
                ", nonLaborIncome=" + nonLaborIncome +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
