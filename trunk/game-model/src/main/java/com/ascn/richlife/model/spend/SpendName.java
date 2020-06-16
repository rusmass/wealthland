package com.ascn.richlife.model.spend;

/**
 * 花费支出
 *
 * Created by zhenhb on 17/4/22.
 * @author zhenhb
 */
public enum SpendName {

    OTHER("其他支出"),TAX("税金");

    private String name;

    public String getName() {
        return name;
    }

    SpendName(String name) {
        this.name = name;
    }
}
