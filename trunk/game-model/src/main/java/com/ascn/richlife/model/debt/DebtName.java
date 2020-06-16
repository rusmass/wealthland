package com.ascn.richlife.model.debt;

/**
 * 负债名称
 */
public enum DebtName {

    HOUSE("住房抵押贷款"),CAR("购车贷款"),EDUCATION("教育贷款"),CREDIT("信用卡"),ADDITIONAL("额外负债");

    private String name;

    public String getName() {
        return name;
    }

    DebtName(String name) {
        this.name = name;
    }
}
