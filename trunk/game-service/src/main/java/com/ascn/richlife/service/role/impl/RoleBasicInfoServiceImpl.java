package com.ascn.richlife.service.role.impl;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleBasicInfo;
import com.ascn.richlife.service.role.RoleBasicInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * 角色基本信息服务
 *
 * Created by Administrator on 2017/7/7 0007.
 */
@Service("roleBasicInfoService")
public class RoleBasicInfoServiceImpl implements RoleBasicInfoService {

    Logger logger = Logger.getLogger(RoleBasicInfoServiceImpl.class);

    @Override
    public RoleBasicInfo init(Role role) {
        RoleBasicInfo roleBasicInfo = new RoleBasicInfo();
        logger.debug("初始化角色id");
        roleBasicInfo.setId(role.getId());

        logger.debug("初始化角色名称");
        roleBasicInfo.setName(role.getName());

        logger.debug("初始化角色年龄");
        roleBasicInfo.setAge(role.getAge());

        logger.debug("初始化角色性别");
        roleBasicInfo.setGender(role.getGender());

        logger.debug("初始化角色职业");
        roleBasicInfo.setProfessional(role.getProfessional());

        logger.debug("初始化角色简介");
        roleBasicInfo.setIntroduction(role.getIntroduction());

        logger.debug("初始化角色工资");
        roleBasicInfo.setWage(role.getWage());

        logger.debug("初始化角色生孩子花费");
        roleBasicInfo.setGiveChildMoney(role.getHaveChild());

        logger.debug("初始化角色银行存储");
        roleBasicInfo.setBankSavings(role.getBankSavings());

        return roleBasicInfo;
    }
}
