package com.ascn.richlife.model.card;

/**
 * 外圈命运
 */
public class OuterFate {

    /**
     * id
     */
    private int id;

    /**
     * 名称
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
     * 处理的类型
     */
    private int processType;

    /**
     * 是否处理资金
     */
    private int ifProcessMoney;

    /**
     * 资金对应的处理数
     */
    private int processMoney;

    /**
     * 处理的方法
     */
    private int processWay;

    /**
     * 关联的ID
     */
    private int relevanceId;

    /**
     * 是否处理非劳务资金
     */
    private int ifProcessNonLobarIncome;

    /**
     * 非劳务变化类型
     */
    private int nonLobarIncomeChangeType;

    /**
     * 非劳务收入变化
     */
    private int nonLobarIncomeChange;

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

    public OuterFate setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OuterFate setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public OuterFate setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public OuterFate setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getProcessType() {
        return processType;
    }

    public OuterFate setProcessType(int processType) {
        this.processType = processType;
        return this;
    }

    public int getIfProcessMoney() {
        return ifProcessMoney;
    }

    public OuterFate setIfProcessMoney(int ifProcessMoney) {
        this.ifProcessMoney = ifProcessMoney;
        return this;
    }

    public int getProcessMoney() {
        return processMoney;
    }

    public OuterFate setProcessMoney(int processMoney) {
        this.processMoney = processMoney;
        return this;
    }

    public int getProcessWay() {
        return processWay;
    }

    public OuterFate setProcessWay(int processWay) {
        this.processWay = processWay;
        return this;
    }

    public int getRelevanceId() {
        return relevanceId;
    }

    public OuterFate setRelevanceId(int relevanceId) {
        this.relevanceId = relevanceId;
        return this;
    }

    public int getIfProcessNonLobarIncome() {
        return ifProcessNonLobarIncome;
    }

    public OuterFate setIfProcessNonLobarIncome(int ifProcessNonLobarIncome) {
        this.ifProcessNonLobarIncome = ifProcessNonLobarIncome;
        return this;
    }

    public int getNonLobarIncomeChangeType() {
        return nonLobarIncomeChangeType;
    }

    public OuterFate setNonLobarIncomeChangeType(int nonLobarIncomeChangeType) {
        this.nonLobarIncomeChangeType = nonLobarIncomeChangeType;
        return this;
    }

    public int getNonLobarIncomeChange() {
        return nonLobarIncomeChange;
    }

    public OuterFate setNonLobarIncomeChange(int nonLobarIncomeChange) {
        this.nonLobarIncomeChange = nonLobarIncomeChange;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public OuterFate setCardIntegral(int cardIntegral) {
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
        return "OuterFate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", processType=" + processType +
                ", ifProcessMoney=" + ifProcessMoney +
                ", processMoney=" + processMoney +
                ", processWay=" + processWay +
                ", relevanceId=" + relevanceId +
                ", ifProcessNonLobarIncome=" + ifProcessNonLobarIncome +
                ", nonLobarIncomeChangeType=" + nonLobarIncomeChangeType +
                ", nonLobarIncomeChange=" + nonLobarIncomeChange +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}

