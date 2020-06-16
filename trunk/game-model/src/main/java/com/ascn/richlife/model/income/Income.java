package com.ascn.richlife.model.income;

/**
 * 收入
 */
public class Income {

    /**
     * 收入的名称
     */
    private String name;

    /**
     * 收入的金钱
     */
    private int money;

    public String getName() {
        return name;
    }

    public Income setName(String name) {
        this.name = name;
        return this;
    }

    public int getMoney() {
        return money;
    }

    public Income setMoney(int money) {
        this.money = money;
        return this;
    }
}
