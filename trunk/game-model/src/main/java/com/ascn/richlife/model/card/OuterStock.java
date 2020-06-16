package com.ascn.richlife.model.card;

/**
 * 外圈股票
 */
public class OuterStock {

    /**
     * id
     */
    private int id;

    /**
     * 所属大类型
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
     * 描述
     */
    private String instructions;

    /**
     * 基金股票代码
     */
    private String stockCode;

    /**
     * 票据名称
     */
    private String billName;

    /**
     * 今日价格
     */
    private int todayPrice;

    /**
     * 投资收益
     */
    private String investmentIncome;

    /**
     * 分红
     */
    private String dividend;

    /**
     * 品质积分
     */
    private int qualityIntegral;

    /**
     * 品质积分说明
     */
    private String qualityIntegralInstruction;

    /**
     * 非劳务收入
     */
    private int nonLaborIncome;

    /**
     * 价格范围
     */
    private String priceScope;

    /**
     * 拥有的股票数
     */
    private int stockNumber;

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

    public OuterStock setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public OuterStock setType(int type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public OuterStock setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public OuterStock setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public OuterStock setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public String getStockCode() {
        return stockCode;
    }

    public OuterStock setStockCode(String stockCode) {
        this.stockCode = stockCode;
        return this;
    }

    public String getBillName() {
        return billName;
    }

    public OuterStock setBillName(String billName) {
        this.billName = billName;
        return this;
    }

    public int getTodayPrice() {
        return todayPrice;
    }

    public OuterStock setTodayPrice(int todayPrice) {
        this.todayPrice = todayPrice;
        return this;
    }

    public String getInvestmentIncome() {
        return investmentIncome;
    }

    public OuterStock setInvestmentIncome(String investmentIncome) {
        this.investmentIncome = investmentIncome;
        return this;
    }

    public String getDividend() {
        return dividend;
    }

    public OuterStock setDividend(String dividend) {
        this.dividend = dividend;
        return this;
    }

    public int getQualityIntegral() {
        return qualityIntegral;
    }

    public OuterStock setQualityIntegral(int qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
        return this;
    }

    public String getQualityIntegralInstruction() {
        return qualityIntegralInstruction;
    }

    public OuterStock setQualityIntegralInstruction(String qualityIntegralInstruction) {
        this.qualityIntegralInstruction = qualityIntegralInstruction;
        return this;
    }

    public int getNonLaborIncome() {
        return nonLaborIncome;
    }

    public OuterStock setNonLaborIncome(int nonLaborIncome) {
        this.nonLaborIncome = nonLaborIncome;
        return this;
    }

    public String getPriceScope() {
        return priceScope;
    }

    public OuterStock setPriceScope(String priceScope) {
        this.priceScope = priceScope;
        return this;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public OuterStock setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
        return this;
    }

    public int getCardIntegral() {
        return cardIntegral;
    }

    public OuterStock setCardIntegral(int cardIntegral) {
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
        return "OuterStock{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", instructions='" + instructions + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", billName='" + billName + '\'' +
                ", todayPrice=" + todayPrice +
                ", investmentIncome='" + investmentIncome + '\'' +
                ", dividend='" + dividend + '\'' +
                ", qualityIntegral=" + qualityIntegral +
                ", qualityIntegralInstruction='" + qualityIntegralInstruction + '\'' +
                ", nonLaborIncome=" + nonLaborIncome +
                ", priceScope='" + priceScope + '\'' +
                ", stockNumber=" + stockNumber +
                ", cardIntegral=" + cardIntegral +
                ", giveUpIntegral=" + giveUpIntegral +
                '}';
    }
}
