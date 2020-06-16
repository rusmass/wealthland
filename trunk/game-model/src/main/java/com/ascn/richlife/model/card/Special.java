package com.ascn.richlife.model.card;

/**
 * 特殊卡牌
 */
public class Special {

    /**
     * id
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 说明
     */
    private String instructions;

    /**
     * 路径
     */
    private String path;

    /**
     * 卡牌积分
     */
    private int cardIntegral;

    /**
     * 放弃的积分
     */
    private int giveUpIntegral;

    /**
     * 提示数据
     */
    private Object tipData;

    public int getId() {
        return id;
    }

    public Special setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Special setName(String name) {
        this.name = name;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public Special setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Special setPath(String path) {
        this.path = path;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public Special setCardIntegral(int cardIntegral) {
        this.cardIntegral = cardIntegral;
        return this;
    }

    public int getGiveUpIntegral() {
        return giveUpIntegral;
    }

    public void setGiveUpIntegral(int giveUpIntegral) {
        this.giveUpIntegral = giveUpIntegral;
    }

    public Object getTipData() {
        return tipData;
    }

    public void setTipData(Object tipData) {
        this.tipData = tipData;
    }

    @Override
    public String toString() {
        return "Special{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", path='" + path + '\'' +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
