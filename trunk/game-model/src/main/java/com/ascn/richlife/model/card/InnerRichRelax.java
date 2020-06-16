package com.ascn.richlife.model.card;

/**
 * 有钱有闲卡牌
 */
public class InnerRichRelax {

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
     * 说明
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
     * 流动现金
     */
    private int flowCash;

    /**
     * 时间积分
     */
    private int timeIntegral;

    /**
     * 卡牌积分
     */
    private int cardIntegral;

    /**
     * 放弃的积分
     */
    private int giveUpIntegral;

    public int getId() {
        return id;
    }

    public InnerRichRelax setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InnerRichRelax setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public InnerRichRelax setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public InnerRichRelax setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getInvestmentPay() {
        return investmentPay;
    }

    public InnerRichRelax setInvestmentPay(int investmentPay) {
        this.investmentPay = investmentPay;
        return this;
    }

    public String getInvestmentIncome() {
        return investmentIncome;
    }

    public InnerRichRelax setInvestmentIncome(String investmentIncome) {
        this.investmentIncome = investmentIncome;
        return this;
    }

    public int getFlowCash() {
        return flowCash;
    }

    public InnerRichRelax setFlowCash(int flowCash) {
        this.flowCash = flowCash;
        return this;
    }

    public int getTimeIntegral() {
        return timeIntegral;
    }

    public InnerRichRelax setTimeIntegral(int timeIntegral) {
        this.timeIntegral = timeIntegral;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public InnerRichRelax setCardIntegral(int cardIntegral) {
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
        return "InnerRichRelax{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", investmentPay=" + investmentPay +
                ", investmentIncome='" + investmentIncome + '\'' +
                ", flowCash=" + flowCash +
                ", timeIntegral=" + timeIntegral +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
