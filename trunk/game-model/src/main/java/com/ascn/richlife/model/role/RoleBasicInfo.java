package com.ascn.richlife.model.role;

/**
 * 角色模型的基本信息
 */
public class RoleBasicInfo {

    /**
     * id
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private int gender;

    /**
     * 职业
     */
    private String professional;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 工资
     */
    private int wage;

    /**
     * 生孩子花费
     */
    private int giveChildMoney;

    /**
     * 银行储蓄
     */
    private int bankSavings;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public String getProfessional() {
        return professional;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getWage() {
        return wage;
    }

    public int getGiveChildMoney() {
        return giveChildMoney;
    }

    public int getBankSavings() {
        return bankSavings;
    }

    public RoleBasicInfo setId(int id) {
        this.id = id;
        return this;
    }

    public RoleBasicInfo setName(String name) {
        this.name = name;
        return this;
    }

    public RoleBasicInfo setAge(int age) {
        this.age = age;
        return this;
    }

    public RoleBasicInfo setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public RoleBasicInfo setProfessional(String professional) {
        this.professional = professional;
        return this;
    }

    public RoleBasicInfo setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public RoleBasicInfo setWage(int wage) {
        this.wage = wage;
        return this;
    }

    public RoleBasicInfo setGiveChildMoney(int giveChildMoney) {
        this.giveChildMoney = giveChildMoney;
        return this;
    }

    public RoleBasicInfo setBankSavings(int bankSavings) {
        this.bankSavings = bankSavings;
        return this;
    }
}
