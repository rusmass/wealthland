package com.ascn.richlife.model.role;

import com.ascn.richlife.model.card.OuterBigChance;
import com.ascn.richlife.model.card.OuterSmallChance;
import com.ascn.richlife.model.card.OuterStock;

import java.util.List;

/**
 * 角色拥有资产信息
 */
public class RoleHaveAssetInfo {

    /**
     * 资产总金额
     */
    private int assetTotalMoney;

    /**
     * 小机会资产
     */
    private List<OuterSmallChance> smallChances;

    /**
     * 股票
     */
    private List<OuterStock> stocks;

    /**
     * 大机会
     */
    private List<OuterBigChance> bigChances;

    public int getAssetTotalMoney() {
        return assetTotalMoney;
    }

    public RoleHaveAssetInfo setAssetTotalMoney(int assetTotalMoney) {
        this.assetTotalMoney = assetTotalMoney;
        return this;
    }

    public List<OuterSmallChance> getSmallChances() {
        return smallChances;
    }

    public RoleHaveAssetInfo setSmallChances(List<OuterSmallChance> smallChances) {
        this.smallChances = smallChances;
        return this;
    }

    public List<OuterStock> getStocks() {
        return stocks;
    }

    public RoleHaveAssetInfo setStocks(List<OuterStock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public List<OuterBigChance> getBigChances() {
        return bigChances;
    }

    public RoleHaveAssetInfo setBigChances(List<OuterBigChance> bigChances) {
        this.bigChances = bigChances;
        return this;
    }
}
