package com.ascn.richlife.model.role;

import java.util.List;

/**
 * 售出资产记录
 */
public class RoleSellAssetRecord {

    /**
     * 资产出售记录
     */
    private List<RoleSellAssetInfo> roleSellAssetRecord;

    public List<RoleSellAssetInfo> getRoleSellAssetRecord() {
        return roleSellAssetRecord;
    }

    public RoleSellAssetRecord setRoleSellAssetRecord(List<RoleSellAssetInfo> roleSellAssetRecord) {
        this.roleSellAssetRecord = roleSellAssetRecord;
        return this;
    }
}
