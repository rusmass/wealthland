package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleModelInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 角色模型信息管理
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Component("roleModelInfoHandler")
public class RoleModelInfoHandler {

    private Logger logger = Logger.getLogger(RoleModelInfoHandler.class);

    public RoleModelInfo init(Role role) {

        RoleModelInfo roleModelInfo = new RoleModelInfo();

        logger.debug("初始化模型id...");
        roleModelInfo.setModelId(role.getModel());

        logger.debug("初始化模型路径...");
        roleModelInfo.setModelPath(role.getModelSave());

        logger.debug("初始头像信息...");
        roleModelInfo.setHeadImgInfo(role.getHeadImg());

        logger.debug("初始化人物信息...");
        roleModelInfo.setPersonImgInfo(role.getPersonImg());

        return roleModelInfo;
    }
}
