package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RolePayoutInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色支出信息管理
 *
 * Created by zhangpengxiang on 17/4/7.
 */
@Component("rolePayoutInfoHandler")
public class RolePayoutInfoHandler {

    private Logger logger = Logger.getLogger(RolePayoutInfoHandler.class);

    public RolePayoutInfo init(Role role) {

        RolePayoutInfo rolePayoutInfo = new RolePayoutInfo();

        logger.debug("初始化基础支出");
        Map<String, Integer> basicPayout = new HashMap<String, Integer>();

        logger.debug("初始化角色住房支出");
        basicPayout.put("house", role.getHouseInterest());

        logger.debug("初始化角色教育支出");
        basicPayout.put("education", role.getEducationInterest());

        logger.debug("初始化角色购车支出");
        basicPayout.put("car", role.getCarInterest());

        logger.debug("初始化角色信用卡支出");
        basicPayout.put("credit", role.getCreditCardInterest());

        logger.debug("初始化角色额外支出");
        basicPayout.put("additional", role.getAdditionalInterest());

        logger.debug("初始化角色其他支出");
        basicPayout.put("other", role.getOtherInterest());

        logger.debug("初始化角色税金");
        basicPayout.put("tax", role.getTax());

        logger.debug("初始化新增支出");
        Map<String, Integer> addNewPayout = new HashMap<String, Integer>();

        rolePayoutInfo.setBasicPayout(basicPayout);

        rolePayoutInfo.setAddNewPayout(addNewPayout);

        return rolePayoutInfo;

    }
}
