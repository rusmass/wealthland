package com.ascn.richlife.service.role.impl;


import com.ascn.richlife.model.integral.Integral;
import com.ascn.richlife.model.role.RoleDataManageInfo;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleIntegralRecord;
import com.ascn.richlife.service.role.RoleDataManageInfoService;
import com.ascn.richlife.service.role.RoleIntegralRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色积分记录服务
 *
 * Created by zhangpengxiang on 17/4/20.
 */
@Service("roleIntegralRecordService")
public class RoleIntegralRecordServiceImpl implements RoleIntegralRecordService {

    @Autowired
    private RoleDataManageInfoService roleDataManageInfoService;

    @Override
    public RoleIntegralRecord init() {

        RoleIntegralRecord roleIntegralRecord = new RoleIntegralRecord();

        //时间积分
        List<Integral> timeIntegral = new ArrayList<Integral>();

//        Integral one = new Integral();
//        one.setName("三室两厅");
//        one.setIntegral(1);
//
//        Integral two = new Integral();
//        two.setName("一辆坦克");
//        two.setIntegral(1);
//
//        timeIntegral.add(one);
//        timeIntegral.add(two);

        //品质积分
        List<Integral> qualityIntegral = new ArrayList<Integral>();

        //流动现金记录
        List<Integral> flowCashIntegral = new ArrayList<Integral>();

//        Integral qualityOne = new Integral();
//        qualityOne.setName("一架飞机");
//        qualityOne.setIntegral(1);
//        Integral qualitytwo = new Integral();
//        qualitytwo.setName("一架飞机");
//        qualitytwo.setIntegral(-1);
//
//        qualityIntegral.add(qualityOne);
//        qualityIntegral.add(qualitytwo);

        roleIntegralRecord.setTimeIntegral(timeIntegral);

        roleIntegralRecord.setQualityIntegral(qualityIntegral);

        roleIntegralRecord.setFlowCashIntegral(flowCashIntegral);

        roleIntegralRecord.setTimeTotalIntegral(0);

        roleIntegralRecord.setQualityTotalIntegral(0);

        roleIntegralRecord.setFlowCashTotalIntegral(0);

        return roleIntegralRecord;
    }

    @Override
    public void innerInit(RoleIntegralRecord roleIntegralRecord) {

        roleIntegralRecord.setQualityTotalIntegral(roleIntegralRecord.getQualityTotalIntegral() * 10);

        roleIntegralRecord.setTimeTotalIntegral(roleIntegralRecord.getTimeTotalIntegral() * 100);

    }

    @Override
    public void updateTimeTotalIntegral(RoleInfo roleInfo, int number) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //更新时间积分
        roleIntegralRecord.setTimeTotalIntegral(roleIntegralRecord.getTimeTotalIntegral() + number);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //更新角色数据中的时间积分
        roleDataManageInfoService.updateRoleTimeIntegral(roleDataManageInfo, roleIntegralRecord.getTimeTotalIntegral());
    }

    @Override
    public void updateQualityTotalIntegral(RoleInfo roleInfo, int number) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //更新品质积分
        roleIntegralRecord.setQualityTotalIntegral(roleIntegralRecord.getQualityTotalIntegral() + number);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //更新角色数据中的品质积分
        roleDataManageInfoService.updateRoleQualityIntegral(roleDataManageInfo, roleIntegralRecord.getQualityTotalIntegral());
    }

    @Override
    public void updateFlowCashIntegralIntegral(RoleInfo roleInfo, int number) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //更新流动资金
        roleIntegralRecord.setFlowCashTotalIntegral(roleIntegralRecord.getFlowCashTotalIntegral() + number);

        //更新角色数据中的流动资金
        roleDataManageInfoService.updateRoleFlowCash(roleInfo, roleIntegralRecord.getFlowCashTotalIntegral());

    }


    @Override
    public void addTimeIntegralRecord(RoleInfo roleInfo, Integral integral) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //添加时间积分记录
        roleIntegralRecord.getTimeIntegral().add(integral);

        //更新玩家时间总积分
        updateTimeTotalIntegral(roleInfo, integral.getIntegral());

    }

    @Override
    public void addQualityIntegralRecord(RoleInfo roleInfo, Integral integral) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //添加品质积分记录
        roleIntegralRecord.getQualityIntegral().add(integral);

        //更新玩家品质总积分
        updateQualityTotalIntegral(roleInfo, integral.getIntegral());
    }

    @Override
    public void addFlowCashIntegralRecord(RoleInfo roleInfo, Integral integral) {

        //获取玩家积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        //添加流动资金记录
        roleIntegralRecord.getFlowCashIntegral().add(integral);

        //更新玩家流动资金
        updateFlowCashIntegralIntegral(roleInfo, integral.getIntegral());


    }
}
