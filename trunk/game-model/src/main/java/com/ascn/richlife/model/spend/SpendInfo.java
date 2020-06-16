package com.ascn.richlife.model.spend;

/**
 * 花费信息
 */
public class SpendInfo {

    /**
     * 支出名称
     */
    private String name;

    /**
     * 支出金钱
     */
    private int money;

    public String getName() {
        return name;
    }

    public SpendInfo setName(String name) {
        this.name = name;
        return this;
    }

    public int getMoney() {
        return money;
    }

    public SpendInfo setMoney(int money) {
        this.money = money;
        return this;
    }

    public SpendInfo(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public SpendInfo() {
    }
}
