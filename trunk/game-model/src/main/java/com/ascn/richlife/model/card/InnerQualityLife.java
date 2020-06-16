package com.ascn.richlife.model.card;

/**
 * 品质生活卡牌
 */
public class InnerQualityLife {

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
     * 消费金额
     */
    private int consumeMoney;

    /**
     * 时间积分
     */
    private int timeIntegral;

    /**
     * 品质积分
     */
    private int qualityIntegral;

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

    public InnerQualityLife setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InnerQualityLife setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public InnerQualityLife setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public InnerQualityLife setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public int getConsumeMoney() {
        return consumeMoney;
    }

    public InnerQualityLife setConsumeMoney(int consumeMoney) {
        this.consumeMoney = consumeMoney;
        return this;
    }

    public int getTimeIntegral() {
        return timeIntegral;
    }

    public InnerQualityLife setTimeIntegral(int timeIntegral) {
        this.timeIntegral = timeIntegral;
        return this;
    }

    public int getQualityIntegral() {
        return qualityIntegral;
    }

    public InnerQualityLife setQualityIntegral(int qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public InnerQualityLife setCardIntegral(int cardIntegral) {
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
        return "InnerQualityLife{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", consumeMoney=" + consumeMoney +
                ", timeIntegral=" + timeIntegral +
                ", qualityIntegral=" + qualityIntegral +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
