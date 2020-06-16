package com.ascn.richlife.model.role;

/**
 * 游戏中角色模型
 */
public class Role {

    /**
     * 角色id
     */
    private int id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 职业
     */
    private String professional;

    /**
     * 年龄
     */
    private int age;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 性别
     */
    private int gender;

    /**
     * 工资-总收入
     */
    private int wage;

    /**
     * 角色路径
     */
    private String headImg;

    /**
     * 任务图片
     */
    private String personImg;

    /**
     * 模型
     */
    private int model;

    /**
     * 模型路径
     */
    private String modelSave;

    /**
     * 生孩子花费
     */
    private int haveChild;

    /**
     * 银行储蓄
     */
    private int bankSavings;

    /**
     * 总贷款
     */
    private int houseLoan;

    /**
     * 教育贷款
     */
    private int educationLoan;

    /**
     * 购车贷款
     */
    private int carLoan;

    /**
     * 信用卡
     */
    private int creditCard;

    /**
     * 额外负债
     */
    private int additionalDebt;

    /**
     * 税金
     */
    private int tax;

    /**
     * 总利息
     */
    private int houseInterest;

    /**
     * 教育利息
     */
    private int educationInterest;

    /**
     * 购车利息
     */
    private int carInterest;

    /**
     * 信用卡利息
     */
    private int creditCardInterest;

    /**
     * 额外利息
     */
    private int additionalInterest;

    /**
     * 其它利息
     */
    private int otherInterest;

    public int getId() {
        return id;
    }

    public Role setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfessional() {
        return professional;
    }

    public Role setProfessional(String professional) {
        this.professional = professional;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Role setAge(int age) {
        this.age = age;
        return this;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Role setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public Role setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public int getWage() {
        return wage;
    }

    public Role setWage(int wage) {
        this.wage = wage;
        return this;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Role setHeadImg(String headImg) {
        this.headImg = headImg;
        return this;
    }

    public String getPersonImg() {
        return personImg;
    }

    public Role setPersonImg(String personImg) {
        this.personImg = personImg;
        return this;
    }

    public int getModel() {
        return model;
    }

    public Role setModel(int model) {
        this.model = model;
        return this;
    }

    public String getModelSave() {
        return modelSave;
    }

    public Role setModelSave(String modelSave) {
        this.modelSave = modelSave;
        return this;
    }

    public int getHaveChild() {
        return haveChild;
    }

    public Role setHaveChild(int haveChild) {
        this.haveChild = haveChild;
        return this;
    }

    public int getBankSavings() {
        return bankSavings;
    }

    public Role setBankSavings(int bankSavings) {
        this.bankSavings = bankSavings;
        return this;
    }

    public int getHouseLoan() {
        return houseLoan;
    }

    public Role setHouseLoan(int houseLoan) {
        this.houseLoan = houseLoan;
        return this;
    }

    public int getEducationLoan() {
        return educationLoan;
    }

    public Role setEducationLoan(int educationLoan) {
        this.educationLoan = educationLoan;
        return this;
    }

    public int getCarLoan() {
        return carLoan;
    }

    public Role setCarLoan(int carLoan) {
        this.carLoan = carLoan;
        return this;
    }

    public int getCreditCard() {
        return creditCard;
    }

    public Role setCreditCard(int creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public int getAdditionalDebt() {
        return additionalDebt;
    }

    public Role setAdditionalDebt(int additionalDebt) {
        this.additionalDebt = additionalDebt;
        return this;
    }

    public int getTax() {
        return tax;
    }

    public Role setTax(int tax) {
        this.tax = tax;
        return this;
    }

    public int getHouseInterest() {
        return houseInterest;
    }

    public Role setHouseInterest(int houseInterest) {
        this.houseInterest = houseInterest;
        return this;
    }

    public int getEducationInterest() {
        return educationInterest;
    }

    public Role setEducationInterest(int educationInterest) {
        this.educationInterest = educationInterest;
        return this;
    }

    public int getCarInterest() {
        return carInterest;
    }

    public Role setCarInterest(int carInterest) {
        this.carInterest = carInterest;
        return this;
    }

    public int getCreditCardInterest() {
        return creditCardInterest;
    }

    public Role setCreditCardInterest(int creditCardInterest) {
        this.creditCardInterest = creditCardInterest;
        return this;
    }

    public int getAdditionalInterest() {
        return additionalInterest;
    }

    public Role setAdditionalInterest(int additionalInterest) {
        this.additionalInterest = additionalInterest;
        return this;
    }

    public int getOtherInterest() {
        return otherInterest;
    }

    public Role setOtherInterest(int otherInterest) {
        this.otherInterest = otherInterest;
        return this;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professional='" + professional + '\'' +
                ", age=" + age +
                ", introduction='" + introduction + '\'' +
                ", gender=" + gender +
                ", wage=" + wage +
                ", headImg='" + headImg + '\'' +
                ", personImg='" + personImg + '\'' +
                ", model=" + model +
                ", modelSave='" + modelSave + '\'' +
                ", haveChild=" + haveChild +
                ", bankSavings=" + bankSavings +
                ", houseLoan=" + houseLoan +
                ", educationLoan=" + educationLoan +
                ", carLoan=" + carLoan +
                ", creditCard=" + creditCard +
                ", additionalDebt=" + additionalDebt +
                ", tax=" + tax +
                ", houseInterest=" + houseInterest +
                ", educationInterest=" + educationInterest +
                ", carInterest=" + carInterest +
                ", creditCardInterest=" + creditCardInterest +
                ", additionalInterest=" + additionalInterest +
                ", otherInterest=" + otherInterest +
                '}';
    }
}
