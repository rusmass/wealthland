package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.income.LaborIncome;
import com.ascn.richlife.model.income.NonLaborIncome;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleIncomeInfo;
import com.ascn.richlife.model.role.RoleInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色收入信息
 *
 * Created by zhangpengxiang on 17/4/3.
 */
@Component("roleIncomeInfoHandler")
public class RoleIncomeInfoHandler {

    private Logger logger = Logger.getLogger(RoleIncomeInfoHandler.class);

    @Autowired
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    public RoleIncomeInfo init(Role role) {

        RoleIncomeInfo roleIncomeInfo = new RoleIncomeInfo();

        //初始化角色劳务收入
        LaborIncome laborIncome = new LaborIncome();
        laborIncome.setName("工资");
        laborIncome.setMoney(role.getWage());

        //初始化角色非劳务收入
        List<NonLaborIncome> nonLaborIncomeList = new ArrayList<NonLaborIncome>();
        roleIncomeInfo.setLaborIncome(laborIncome);
        roleIncomeInfo.setNonLaborIncomeList(nonLaborIncomeList);

        //初始化非劳务总收入
        roleIncomeInfo.setTotalNonLaborIncome(0);

        //初始化总收入
        roleIncomeInfo.setTotalIncome(role.getWage() + roleIncomeInfo.getTotalNonLaborIncome());

        return roleIncomeInfo;
    }

    public void addNonLaborIncome(RoleInfo roleInfo, NonLaborIncome nonLaborIncome) {

        //获取角色的收入信息
        RoleIncomeInfo roleIncomeInfo = roleInfo.getRoleIncomeInfo();

        //添加非劳务收入
        roleIncomeInfo.getNonLaborIncomeList().add(nonLaborIncome);

        //更新非劳务总收入
        updateTotalNonLaborIncome(roleInfo, nonLaborIncome.getMoney());
    }

    public void removeNonLaborIncome(RoleInfo roleInfo, NonLaborIncome nonLaborIncome) {

        //获取角色非劳务收入信息
        List<NonLaborIncome> nonLaborIncomeList = roleInfo.getRoleIncomeInfo().getNonLaborIncomeList();

        //判断玩家是否有该收入,如果有的话则删除
        boolean ifExist = false;


        //根据cardId删除非劳务收入
        for (int i = 0; i < nonLaborIncomeList.size(); i++) {
            if (nonLaborIncomeList.get(i).getId() == nonLaborIncome.getId()) {
                nonLaborIncomeList.remove(i);
                ifExist = true;
            }
        }

        if (!ifExist) {
            logger.error("删除失败,没有这个非劳务收入");
            return;
        }

        //更新非劳务总收入
        updateTotalNonLaborIncome(roleInfo, -nonLaborIncome.getMoney());

    }

    /**
     * 更新非劳务总收入
     *
     * @param roleInfo
     * @param money
     */
    private void updateTotalNonLaborIncome(RoleInfo roleInfo, int money) {

        //获取角色收入信息
        RoleIncomeInfo roleIncomeInfo = roleInfo.getRoleIncomeInfo();

        //更新非劳务总收入
        roleIncomeInfo.setTotalNonLaborIncome(roleIncomeInfo.getTotalNonLaborIncome() + money);

        //更新总收入
        roleIncomeInfo.setTotalIncome(roleIncomeInfo.getLaborIncome().getMoney() + roleIncomeInfo.getTotalNonLaborIncome());

        //更新数据信息
        roleDataManageInfoHandler.updateRoleNonLaborIncome(roleInfo.getRoleDataManageInfo(), roleIncomeInfo.getTotalNonLaborIncome());

        //更新数据信息
        roleDataManageInfoHandler.updateRoleTotalIncome(roleInfo, roleIncomeInfo.getTotalIncome());
    }

}
