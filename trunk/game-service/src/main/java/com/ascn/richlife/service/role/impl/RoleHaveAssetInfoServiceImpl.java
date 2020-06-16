package com.ascn.richlife.service.role.impl;

import com.ascn.richlife.model.card.OuterBigChance;
import com.ascn.richlife.model.card.OuterSmallChance;
import com.ascn.richlife.model.card.OuterStock;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleHaveAssetInfo;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.service.role.RoleHaveAssetInfoService;
import org.springframework.stereotype.Service;

/**
 * 角色拥有资产信息服务
 *
 * Created by Administrator on 2017/7/7 0007.
 */
@Service("roleHaveAssetInfoService")
public class RoleHaveAssetInfoServiceImpl implements RoleHaveAssetInfoService{
    @Override
    public RoleHaveAssetInfo init(Role role) {
        return null;
    }

    @Override
    public void addSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance) {

    }

    @Override
    public void addStock(RoleInfo roleInfo, OuterStock outerStock, int number) {

    }

    @Override
    public void addBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance) {

    }

    @Override
    public void removeSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance, int sellPrice) {

    }

    @Override
    public void removeStock(RoleInfo roleInfo, OuterStock outerStock, int number, int sellPrice) {

    }

    @Override
    public void removeBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance, int sellPrice) {

    }
}
