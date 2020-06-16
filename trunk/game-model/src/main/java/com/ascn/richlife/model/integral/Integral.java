package com.ascn.richlife.model.integral;

/**
 * 资产积分
 */
public class Integral {

    /**
     * 资产名称
     */
    private String name;

    /**
     * 积分
     */
    private int integral;

    public String getName() {
        return name;
    }

    public Integral setName(String name) {
        this.name = name;
        return this;
    }

    public int getIntegral() {
        return integral;
    }

    public Integral setIntegral(int integral) {
        this.integral = integral;
        return this;
    }
}
