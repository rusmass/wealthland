package com.ascn.richlife.model.card;

/**
 * 内圈投资卡牌
 */
public class InnerInvestment {

    /**
     * id
     */
    private int id;

    /**
     * 标题
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
     * 投资支付
     */
    private int investmentPay;

    /**
     * 收益率
     */
    private String investmentIncome;

    /**
     * 流动资金
     */
    private int flowCash;

    /**
     * 是否掷骰子
     */
    private int ifRollDice;

    /**
     * 掷骰子满足条件
     */
    private int diceConditions;

    /**
     * 骰子的值
     */
    private int diceNumber;

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

    public InnerInvestment setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InnerInvestment setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public InnerInvestment setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public InnerInvestment setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getInvestmentPay() {
        return investmentPay;
    }

    public InnerInvestment setInvestmentPay(int investmentPay) {
        this.investmentPay = investmentPay;
        return this;
    }

    public String getInvestmentIncome() {
        return investmentIncome;
    }

    public InnerInvestment setInvestmentIncome(String investmentIncome) {
        this.investmentIncome = investmentIncome;
        return this;
    }

    public int getFlowCash() {
        return flowCash;
    }

    public InnerInvestment setFlowCash(int flowCash) {
        this.flowCash = flowCash;
        return this;
    }

    public int getIfRollDice() {
        return ifRollDice;
    }

    public InnerInvestment setIfRollDice(int ifRollDice) {
        this.ifRollDice = ifRollDice;
        return this;
    }

    public int getDiceConditions() {
        return diceConditions;
    }

    public InnerInvestment setDiceConditions(int diceConditions) {
        this.diceConditions = diceConditions;
        return this;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public InnerInvestment setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public InnerInvestment setCardIntegral(int cardIntegral) {
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
        return "InnerInvestment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", investmentPay=" + investmentPay +
                ", investmentIncome='" + investmentIncome + '\'' +
                ", flowCash=" + flowCash +
                ", ifRollDice=" + ifRollDice +
                ", diceConditions=" + diceConditions +
                ", diceNumber=" + diceNumber +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
