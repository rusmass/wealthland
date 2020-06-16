package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleBasicInfo;
import org.springframework.stereotype.Component;

/**
 * 角色基本信息
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Component("roleBasicInfoHandler")
public class RoleBasicInfoHandler{

    public RoleBasicInfo init(Role role) {

        RoleBasicInfo roleBasicInfo = new RoleBasicInfo();

        roleBasicInfo.setId(role.getId());

        roleBasicInfo.setName(role.getName());

        roleBasicInfo.setAge(role.getAge());

        roleBasicInfo.setGender(role.getGender());

        roleBasicInfo.setProfessional(role.getProfessional());

        roleBasicInfo.setIntroduction(role.getIntroduction());

        roleBasicInfo.setWage(role.getWage());

        roleBasicInfo.setGiveChildMoney(role.getHaveChild());

        roleBasicInfo.setBankSavings(role.getBankSavings());

        return roleBasicInfo;
    }

}