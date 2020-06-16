package com.ascn.richlife.service.role.impl;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleSellAssetInfo;
import com.ascn.richlife.model.role.RoleSellAssetRecord;
import com.ascn.richlife.service.role.RoleSellAssetRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色售出资产信息
 *
 * Created by zhangpengxiang on 17/4/10.
 */
@Service("roleSellAssetRecordService")
public class RoleSellAssetRecordServiceImpl implements RoleSellAssetRecordService {


    @Override
    public RoleSellAssetRecord init(Role role) {

        RoleSellAssetRecord roleSellAssetRecord = new RoleSellAssetRecord();

        List<RoleSellAssetInfo> roleSellAssetInfoList = new ArrayList<RoleSellAssetInfo>();

        roleSellAssetRecord.setRoleSellAssetRecord(roleSellAssetInfoList);

        return roleSellAssetRecord;
    }

    @Override
    public void addRoleSellAssetRecord(RoleSellAssetRecord roleSellAssetRecord,RoleSellAssetInfo roleSellAssetInfo) {

        roleSellAssetRecord.getRoleSellAssetRecord().add(roleSellAssetInfo);

    }
}
