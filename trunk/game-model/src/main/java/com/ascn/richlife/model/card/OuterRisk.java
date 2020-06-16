package com.ascn.richlife.model.card;

/**
 * 外圈风险
 */
public class OuterRisk {

    /**
     * id
     */
    private int id;

    /**
     * 种类
     */
    private int type;

    /**
     * 名称
     */
    private String name;

    /**
     * 卡牌路径
     */
    private String path;

    /**
     * 描述1
     */
    private String instructionsOne;

    /**
     * 费用1
     */
    private int moneyOne;

    /**
     * 描述2
     */
    private String instructionsTwo;

    /**
     * 费用2
     */
    private int moneyTwo;

    /**
     * 积分种类
     */
    private int integralType;

    /**
     * 积分名称
     */
    private String integralName;

    /**
     * 积分值
     */
    private int integralValue;

    /**
     * 积分描述
     */
    private String integralInstruction;

    /**
     * 卡牌积分
     */
    private int cardIntegral;

    public int getId() {
        return id;
    }

    public OuterRisk setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public OuterRisk setType(int type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public OuterRisk setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public OuterRisk setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructionsOne() {
        return instructionsOne;
    }

    public OuterRisk setInstructionsOne(String instructionsOne) {
        this.instructionsOne = instructionsOne;
        return this;
    }

    public int getMoneyOne() {
        return moneyOne;
    }

    public OuterRisk setMoneyOne(int moneyOne) {
        this.moneyOne = moneyOne;
        return this;
    }

    public String getInstructionsTwo() {
        return instructionsTwo;
    }

    public OuterRisk setInstructionsTwo(String instructionsTwo) {
        this.instructionsTwo = instructionsTwo;
        return this;
    }

    public int getMoneyTwo() {
        return moneyTwo;
    }

    public OuterRisk setMoneyTwo(int moneyTwo) {
        this.moneyTwo = moneyTwo;
        return this;
    }

    public int getIntegralType() {
        return integralType;
    }

    public OuterRisk setIntegralType(int integralType) {
        this.integralType = integralType;
        return this;
    }

    public String getIntegralName() {
        return integralName;
    }

    public OuterRisk setIntegralName(String integralName) {
        this.integralName = integralName;
        return this;
    }

    public int getIntegralValue() {
        return integralValue;
    }

    public OuterRisk setIntegralValue(int integralValue) {
        this.integralValue = integralValue;
        return this;
    }

    public String getIntegralInstruction() {
        return integralInstruction;
    }

    public OuterRisk setIntegralInstruction(String integralInstruction) {
        this.integralInstruction = integralInstruction;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public OuterRisk setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
        return this;
    }

    @Override
    public String toString() {
        return "OuterRisk{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructionsOne='" + instructionsOne + '\'' +
                ", moneyOne=" + moneyOne +
                ", instructionsTwo='" + instructionsTwo + '\'' +
                ", moneyTwo=" + moneyTwo +
                ", integralType=" + integralType +
                ", integralName='" + integralName + '\'' +
                ", integralValue=" + integralValue +
                ", integralInstruction='" + integralInstruction + '\'' +
                ", cardIntegral=" + cardIntegral +
                '}';
    }
}
