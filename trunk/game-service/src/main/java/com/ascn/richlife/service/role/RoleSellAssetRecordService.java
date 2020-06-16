package com.ascn.richlife.service.role;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleSellAssetInfo;
import com.ascn.richlife.model.role.RoleSellAssetRecord;

/**
 * 角色售出资产记录
 *
 * Created by zhangpengxiang on 17/4/10.
 */
public interface RoleSellAssetRecordService {

    /**
     * 初始化角色出售记录
     *
     * @param role
     * @return
     */
    RoleSellAssetRecord init(Role role);

    /**
     * 添加出售记录
     *
     * @param roleSellAssetRecord
     * @param roleSellAssetInfo
     */
    void addRoleSellAssetRecord(RoleSellAssetRecord roleSellAssetRecord, RoleSellAssetInfo roleSellAssetInfo);
}
