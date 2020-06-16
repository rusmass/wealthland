package com.ascn.richlife.model.card;

/**
 * 内圈命运卡牌
 */
public class InnerFate {

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
     * 描述
     */
    private String instructions;

    /**
     * 命运类型
     */
    private int type;

    /**
     * 赔付算法
     */
    private int payAlgorithm;

    /**
     * 赔付类型
     */
    private int payType;

    /**
     * 赔付数目
     */
    private int payNumber;

    /**
     * 关联id
     */
    private int correlationId;

    /**
     * 掷骰子奖励数
     */
    private int diceRewardMoney;

    /**
     * 骰子的条件
     */
    private int diceCondition;

    /**
     * 掷骰子的数
     */
    private int diceNumber;

    /**
     * 骰子奖励类型
     */
    private int diceRewardType;

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

    public InnerFate setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InnerFate setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public InnerFate setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public InnerFate setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getType() {
        return type;
    }

    public InnerFate setType(int type) {
        this.type = type;
        return this;
    }

    public int getPayAlgorithm() {
        return payAlgorithm;
    }

    public InnerFate setPayAlgorithm(int payAlgorithm) {
        this.payAlgorithm = payAlgorithm;
        return this;
    }

    public int getPayType() {
        return payType;
    }

    public InnerFate setPayType(int payType) {
        this.payType = payType;
        return this;
    }

    public int getPayNumber() {
        return payNumber;
    }

    public InnerFate setPayNumber(int payNumber) {
        this.payNumber = payNumber;
        return this;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public InnerFate setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public int getDiceRewardMoney() {
        return diceRewardMoney;
    }

    public InnerFate setDiceRewardMoney(int diceRewardMoney) {
        this.diceRewardMoney = diceRewardMoney;
        return this;
    }

    public int getDiceCondition() {
        return diceCondition;
    }

    public InnerFate setDiceCondition(int diceCondition) {
        this.diceCondition = diceCondition;
        return this;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public InnerFate setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
        return this;
    }

    public int getDiceRewardType() {
        return diceRewardType;
    }

    public InnerFate setDiceRewardType(int diceRewardType) {
        this.diceRewardType = diceRewardType;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public InnerFate setCardIntegral(int cardIntegral) {
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
        return "InnerFate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", type=" + type +
                ", payAlgorithm=" + payAlgorithm +
                ", payType=" + payType +
                ", payNumber=" + payNumber +
                ", correlationId=" + correlationId +
                ", diceRewardMoney=" + diceRewardMoney +
                ", diceCondition=" + diceCondition +
                ", diceNumber=" + diceNumber +
                ", diceRewardType=" + diceRewardType +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
