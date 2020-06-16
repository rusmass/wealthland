package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.debt.DebtName;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleSpendInfo;
import com.ascn.richlife.model.spend.SpendInfo;
import com.ascn.richlife.model.spend.SpendName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 角色支出信息管理
 *
 * Created by zhangpengxiang on 17/4/22.
 */
@Component("roleSpendInfoHandler")
public class RoleSpendInfoHandler {

    @Autowired
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    public RoleSpendInfo init(Role role) {

        RoleSpendInfo roleSpendInfo = new RoleSpendInfo();

        //基础支出
        Map<String, SpendInfo> basicSpend = new LinkedHashMap<String, SpendInfo>();

        //住房支出
        SpendInfo house = new SpendInfo(DebtName.HOUSE.getName(), -role.getHouseInterest());

        //教育支出
        SpendInfo education = new SpendInfo(DebtName.EDUCATION.getName(), -role.getEducationInterest());

        //购车支出
        SpendInfo car = new SpendInfo(DebtName.CAR.getName(), -role.getCarInterest());

        //信用卡支出
        SpendInfo credit = new SpendInfo(DebtName.CREDIT.getName(), -role.getCreditCardInterest());

        //额外利息
        SpendInfo additional = new SpendInfo(DebtName.ADDITIONAL.getName(), -role.getAdditionalInterest());

        //其他支出
        SpendInfo other = new SpendInfo(SpendName.OTHER.getName(), -role.getOtherInterest());

        //税金
        SpendInfo tax = new SpendInfo(SpendName.TAX.getName(), -role.getTax());

        basicSpend.put(DebtName.HOUSE.getName(), house);

        basicSpend.put(DebtName.EDUCATION.getName(), education);

        basicSpend.put(DebtName.CAR.getName(), car);

        basicSpend.put(DebtName.CREDIT.getName(), credit);

        basicSpend.put(DebtName.ADDITIONAL.getName(), additional);

        basicSpend.put(SpendName.OTHER.getName(), other);

        basicSpend.put(SpendName.TAX.getName(), tax);

        //新增支出
        Map<String, SpendInfo> addNewSpend = new LinkedHashMap<String, SpendInfo>();

//        SpendInfo spendInfo = new SpendInfo();
//
//        spendInfo.setName("一个球");
//        spendInfo.setMoney(-100);
//
//        addNewSpend.put("一个球",spendInfo);

        roleSpendInfo.setBasicSpend(basicSpend);

        roleSpendInfo.setAddNewSpend(addNewSpend);

        return roleSpendInfo;
    }

    public void addNewSpend(RoleInfo roleInfo, SpendInfo spendInfo) {

        //获取角色支出信息
        RoleSpendInfo roleSpendInfo = roleInfo.getRoleSpendInfo();

        //增加新的支出
        roleSpendInfo.getAddNewSpend().put(spendInfo.getName(), spendInfo);

        //更新总支出
        roleDataManageInfoHandler.updateRoleTotalSpending(roleInfo, Math.abs(spendInfo.getMoney()));


    }

    public void addBasicSpend(RoleInfo roleInfo, SpendInfo spendInfo) {

        //获取角色支出信息
        RoleSpendInfo roleSpendInfo = roleInfo.getRoleSpendInfo();

        if ("生孩子".equals(spendInfo.getName())) {

            //是否存在生孩子
            if (roleSpendInfo.getBasicSpend().containsKey(spendInfo.getName())) {

                //累计支出
                roleSpendInfo.getBasicSpend().get(spendInfo.getName()).setMoney(roleSpendInfo.getBasicSpend().get(spendInfo.getName()).getMoney());

            } else {

                //增加新的支出
                roleSpendInfo.getBasicSpend().put(spendInfo.getName(), spendInfo);

            }

        } else {

            roleSpendInfo.getBasicSpend().put(spendInfo.getName(), spendInfo);
        }

        //增加新的支出
        roleSpendInfo.getBasicSpend().put(spendInfo.getName(), spendInfo);

        //更新总支出
        roleDataManageInfoHandler.updateRoleTotalSpending(roleInfo, Math.abs(spendInfo.getMoney()));

    }

    public void removeBasicSpend(RoleInfo roleInfo, String spendName) {

        //获取角色支出信息
        RoleSpendInfo roleSpendInfo = roleInfo.getRoleSpendInfo();

        //获取支出
        SpendInfo spendInfo = roleSpendInfo.getBasicSpend().get(spendName);

        //更新总支出
        roleDataManageInfoHandler.updateRoleTotalSpending(roleInfo, spendInfo.getMoney());

        //移除支出
        roleSpendInfo.getBasicSpend().remove(spendName);
    }

    public void removeNewSpend(RoleInfo roleInfo, String spendName) {

        //获取角色支出信息
        RoleSpendInfo roleSpendInfo = roleInfo.getRoleSpendInfo();

        //获取支出
        SpendInfo spendInfo = roleSpendInfo.getAddNewSpend().get(spendName);

        //更新总支出
        roleDataManageInfoHandler.updateRoleTotalSpending(roleInfo, spendInfo.getMoney());

        //移除支出
        roleSpendInfo.getAddNewSpend().remove(spendName);


    }
}
