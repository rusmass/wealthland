package com.ascn.richlife.service.role;


import com.ascn.richlife.model.card.OuterBigChance;
import com.ascn.richlife.model.card.OuterSmallChance;
import com.ascn.richlife.model.card.OuterStock;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleHaveAssetInfo;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 角色拥有资产信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
public interface RoleHaveAssetInfoService {

    /**
     * 初始化角色资产信息
     *
     * @param role
     * @return
     */
    RoleHaveAssetInfo init(Role role);

    /**
     * 添加小机会资产
     *
     * @param roleInfo
     * @param outerSmallChance
     */
    void addSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance);

    /**
     * 添加股票
     *
     * @param roleInfo
     * @param outerStock
     * @param number
     */
    void addStock(RoleInfo roleInfo, OuterStock outerStock, int number);

    /**
     * 添加大机会资产
     *
     * @param roleInfo
     * @param outerBigChance
     */
    void addBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance);

    /**
     * 移除小机会资产
     *
     * @param roleInfo
     * @param outerSmallChance
     */
    void removeSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance, int sellPrice);

    /**
     * 移除股票
     *
     * @param roleInfo
     * @param number
     */
    void removeStock(RoleInfo roleInfo, OuterStock outerStock, int number, int sellPrice);

    /**
     * 移除大机会资产
     *
     * @param roleInfo
     * @param outerBigChance
     */
    void removeBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance, int sellPrice);
}
