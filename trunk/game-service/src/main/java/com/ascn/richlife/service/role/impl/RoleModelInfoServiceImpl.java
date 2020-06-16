package com.ascn.richlife.service.role.impl;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleModelInfo;
import com.ascn.richlife.service.role.RoleModelInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 角色模型信息服务
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Service("roleModelInfoService")
public class RoleModelInfoServiceImpl implements RoleModelInfoService {

    private Logger logger = Logger.getLogger(RoleModelInfoServiceImpl.class);

    public RoleModelInfo init(Role role) {

        RoleModelInfo roleModelInfo = new RoleModelInfo();

        logger.debug("初始化模型id");
        roleModelInfo.setModelId(role.getModel());

        logger.debug("初始化模型路径");
        roleModelInfo.setModelPath(role.getModelSave());

        logger.debug("初始头像信息");
        roleModelInfo.setHeadImgInfo(role.getHeadImg());

        logger.debug("初始化人物信息");
        roleModelInfo.setPersonImgInfo(role.getPersonImg());

        return roleModelInfo;
    }
}
