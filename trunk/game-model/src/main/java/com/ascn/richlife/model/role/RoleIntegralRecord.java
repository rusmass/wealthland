package com.ascn.richlife.model.role;

import com.ascn.richlife.model.integral.Integral;

import java.util.List;

/**
 * 角色积分记录
 */
public class RoleIntegralRecord {

    /**
     * 时间总积分
     */
    private int timeTotalIntegral;

    /**
     * 品质总积分
     */
    private int qualityTotalIntegral;

    /**
     * 流动总资金
     */
    private int flowCashTotalIntegral;

    /**
     * 时间积分记录
     */
    private List<Integral> timeIntegral;

    /**
     * 品质积分记录
     */
    private List<Integral> qualityIntegral;

    /**
     * 流动资金记录
     */
    private List<Integral> flowCashIntegral;

    public int getTimeTotalIntegral() {
        return timeTotalIntegral;
    }

    public RoleIntegralRecord setTimeTotalIntegral(int timeTotalIntegral) {
        this.timeTotalIntegral = timeTotalIntegral;
        return this;
    }

    public int getQualityTotalIntegral() {
        return qualityTotalIntegral;
    }

    public RoleIntegralRecord setQualityTotalIntegral(int qualityTotalIntegral) {
        this.qualityTotalIntegral = qualityTotalIntegral;
        return this;
    }

    public int getFlowCashTotalIntegral() {
        return flowCashTotalIntegral;
    }

    public RoleIntegralRecord setFlowCashTotalIntegral(int flowCashTotalIntegral) {
        this.flowCashTotalIntegral = flowCashTotalIntegral;
        return this;
    }

    public List<Integral> getFlowCashIntegral() {
        return flowCashIntegral;
    }

    public RoleIntegralRecord setFlowCashIntegral(List<Integral> flowCashIntegral) {
        this.flowCashIntegral = flowCashIntegral;
        return this;
    }

    public List<Integral> getTimeIntegral() {
        return timeIntegral;
    }

    public RoleIntegralRecord setTimeIntegral(List<Integral> timeIntegral) {
        this.timeIntegral = timeIntegral;
        return this;
    }

    public List<Integral> getQualityIntegral() {
        return qualityIntegral;
    }

    public RoleIntegralRecord setQualityIntegral(List<Integral> qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
        return this;
    }
}
