package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleSellAssetInfo;
import com.ascn.richlife.model.role.RoleSellAssetRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色出售资产记录
 *
 * Created by zhangpengxiang on 17/4/10.
 */
@Component("roleSellAssetRecordHandler")
public class RoleSellAssetRecordHandler {


    public RoleSellAssetRecord init(Role role) {

        RoleSellAssetRecord roleSellAssetRecord = new RoleSellAssetRecord();

        List<RoleSellAssetInfo> roleSellAssetInfoList = new ArrayList<RoleSellAssetInfo>();

        roleSellAssetRecord.setRoleSellAssetRecord(roleSellAssetInfoList);

        return roleSellAssetRecord;
    }

    public void addRoleSellAssetRecord(RoleSellAssetRecord roleSellAssetRecord, RoleSellAssetInfo roleSellAssetInfo) {

        roleSellAssetRecord.getRoleSellAssetRecord().add(roleSellAssetInfo);

    }
}
