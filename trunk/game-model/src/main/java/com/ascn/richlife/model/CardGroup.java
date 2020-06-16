package com.ascn.richlife.model;

import java.util.List;

/**
 * 卡牌卡组
 */
public class CardGroup {

    /**
     * 小机会卡组
     */
    private List<Integer> outerSmallChanceGroup;

    /**
     * 股票卡组
     */
    private List<Integer> outerStockGroup;

    /**
     * 大机会卡组
     */
    private List<Integer> outerBigChanceGroup;

    /**
     * 外圈命运卡组
     */
    private List<Integer> outerFateGroup;

    /**
     * 风险卡组
     */
    private List<Integer> outerRiskGroup;

    /**
     * 内圈命运卡组
     */
    private List<Integer> innerFateGroup;

    /**
     * 内圈投资卡组
     */
    private List<Integer> innerInvestmentGroup;

    /**
     * 内圈品质生活卡组
     */
    private List<Integer> innerQualityLifeGroup;

    /**
     * 内圈有闲有钱卡组
     */
    private List<Integer> innerRelaxGroup;

    /**
     * 特殊卡组
     */
    private List<Integer> specialGroup;

    public List<Integer> getOuterSmallChanceGroup() {
        return outerSmallChanceGroup;
    }

    public CardGroup setOuterSmallChanceGroup(List<Integer> outerSmallChanceGroup) {
        this.outerSmallChanceGroup = outerSmallChanceGroup;
        return this;
    }

    public List<Integer> getOuterStockGroup() {
        return outerStockGroup;
    }

    public CardGroup setOuterStockGroup(List<Integer> outerStockGroup) {
        this.outerStockGroup = outerStockGroup;
        return this;
    }

    public List<Integer> getOuterBigChanceGroup() {
        return outerBigChanceGroup;
    }

    public CardGroup setOuterBigChanceGroup(List<Integer> outerBigChanceGroup) {
        this.outerBigChanceGroup = outerBigChanceGroup;
        return this;
    }

    public List<Integer> getOuterFateGroup() {
        return outerFateGroup;
    }

    public CardGroup setOuterFateGroup(List<Integer> outerFateGroup) {
        this.outerFateGroup = outerFateGroup;
        return this;
    }

    public List<Integer> getOuterRiskGroup() {
        return outerRiskGroup;
    }

    public CardGroup setOuterRiskGroup(List<Integer> outerRiskGroup) {
        this.outerRiskGroup = outerRiskGroup;
        return this;
    }

    public List<Integer> getInnerFateGroup() {
        return innerFateGroup;
    }

    public CardGroup setInnerFateGroup(List<Integer> innerFateGroup) {
        this.innerFateGroup = innerFateGroup;
        return this;
    }

    public List<Integer> getInnerInvestmentGroup() {
        return innerInvestmentGroup;
    }

    public CardGroup setInnerInvestmentGroup(List<Integer> innerInvestmentGroup) {
        this.innerInvestmentGroup = innerInvestmentGroup;
        return this;
    }

    public List<Integer> getInnerQualityLifeGroup() {
        return innerQualityLifeGroup;
    }

    public CardGroup setInnerQualityLifeGroup(List<Integer> innerQualityLifeGroup) {
        this.innerQualityLifeGroup = innerQualityLifeGroup;
        return this;
    }

    public List<Integer> getInnerRelaxGroup() {
        return innerRelaxGroup;
    }

    public CardGroup setInnerRelaxGroup(List<Integer> innerRelaxGroup) {
        this.innerRelaxGroup = innerRelaxGroup;
        return this;
    }

    public List<Integer> getSpecialGroup() {
        return specialGroup;
    }

    public CardGroup setSpecialGroup(List<Integer> specialGroup) {
        this.specialGroup = specialGroup;
        return this;
    }

    @Override
    public String toString() {
        return "CardGroup{" +
                "outerSmallChanceGroup=" + outerSmallChanceGroup +
                ", outerStockGroup=" + outerStockGroup +
                ", outerBigChanceGroup=" + outerBigChanceGroup +
                ", outerFateGroup=" + outerFateGroup +
                ", outerRiskGroup=" + outerRiskGroup +
                ", innerFateGroup=" + innerFateGroup +
                ", innerInvestmentGroup=" + innerInvestmentGroup +
                ", innerQualityLifeGroup=" + innerQualityLifeGroup +
                ", innerRelaxGroup=" + innerRelaxGroup +
                ", specialGroup=" + specialGroup +
                '}';
    }
}
