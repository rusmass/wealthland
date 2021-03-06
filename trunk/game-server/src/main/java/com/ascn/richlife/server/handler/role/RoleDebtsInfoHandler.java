package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.debt.DebtInfo;
import com.ascn.richlife.model.debt.DebtName;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleDebtsInfo;
import com.ascn.richlife.model.role.RoleInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 角色负债信息管理
 *
 * Created by zhangpengxiang on 17/4/2.
 */
@Component("roleDebtsInfoHandler")
public class RoleDebtsInfoHandler {

    private Logger logger = Logger.getLogger(RoleDebtsInfoHandler.class);

    public RoleDebtsInfo init(Role role) {

        RoleDebtsInfo roleDebtsInfo = new RoleDebtsInfo();

        //初始化基础负债
        Map<String, DebtInfo> basicDept = new LinkedHashMap<String, DebtInfo>();

        //住房抵押贷款
        DebtInfo house = new DebtInfo(DebtName.HOUSE.getName(), role.getHouseLoan(), role.getHouseInterest());

        //教育贷款
        DebtInfo education = new DebtInfo(DebtName.EDUCATION.getName(), role.getEducationLoan(), role.getEducationInterest());

        //购车贷款
        DebtInfo car = new DebtInfo(DebtName.CAR.getName(), role.getCarLoan(), role.getCarInterest());

        //信用卡
        DebtInfo credit = new DebtInfo(DebtName.CREDIT.getName(), role.getCreditCard(), role.getCreditCardInterest());

        //额外负债
        DebtInfo additional = new DebtInfo(DebtName.ADDITIONAL.getName(), role.getAdditionalDebt(), role.getAdditionalInterest());

        basicDept.put(DebtName.HOUSE.getName(), house);

        basicDept.put(DebtName.EDUCATION.getName(), education);

        basicDept.put(DebtName.CAR.getName(), car);

        basicDept.put(DebtName.CREDIT.getName(), credit);

        basicDept.put(DebtName.ADDITIONAL.getName(), additional);

        //初始化新增负债
        Map<String, DebtInfo> addNewDept = new LinkedHashMap<String, DebtInfo>();

        roleDebtsInfo.setBasicDebt(basicDept);

        roleDebtsInfo.setAddNewDebt(addNewDept);

        return roleDebtsInfo;
    }

    public void innerInit(RoleDebtsInfo roleDebtsInfo) {

        //清空基础负债
        roleDebtsInfo.getBasicDebt().clear();

    }

    public void addNewDebt(RoleInfo roleInfo, DebtInfo debtInfo) {

        //获取负债信息
        RoleDebtsInfo roleDebtsInfo = roleInfo.getRoleDebtsInfo();
        //添加负债
        roleDebtsInfo.getAddNewDebt().put(debtInfo.getDebtName(), debtInfo);


    }

    public void removeBasicDebt(RoleDebtsInfo roleDebtsInfo, String debtName) {

        //移除负债
        roleDebtsInfo.getBasicDebt().remove(debtName);


    }

    public void removeNewDebt(RoleDebtsInfo roleDebtsInfo, String debtName) {
        roleDebtsInfo.getAddNewDebt().remove(debtName);
    }


}
