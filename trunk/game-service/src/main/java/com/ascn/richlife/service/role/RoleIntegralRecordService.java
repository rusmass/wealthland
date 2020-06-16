package com.ascn.richlife.service.role;


import com.ascn.richlife.model.integral.Integral;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleIntegralRecord;

/**
 * 角色积分记录服务
 *
 * Created by zhangpengxiang on 17/4/20.
 */
public interface RoleIntegralRecordService {

    /**
     * 角色积分记录
     *
     * @return
     */
    RoleIntegralRecord init();

    /**
     * 初始化内圈的积分
     *
     * @param roleIntegralRecord
     */
    void innerInit(RoleIntegralRecord roleIntegralRecord);

    /**
     * 更新时间积分
     *
     * @param roleInfo
     * @param number
     */
    void updateTimeTotalIntegral(RoleInfo roleInfo, int number);

    /**
     * 更新品质积分
     *
     * @param roleInfo
     * @param number
     */
    void updateQualityTotalIntegral(RoleInfo roleInfo, int number);

    /**
     * 更新流动资金
     *
     * @param roleInfo
     * @param number
     */
    void updateFlowCashIntegralIntegral(RoleInfo roleInfo, int number);

    /**
     * 添加时间积分记录
     *
     * @param roleInfo
     * @param integral
     */
    void addTimeIntegralRecord(RoleInfo roleInfo, Integral integral);

    /**
     * 添加品质积分记录
     *
     * @param roleInfo
     * @param integral
     */
    void addQualityIntegralRecord(RoleInfo roleInfo, Integral integral);

    /**
     * 添加流动资金记录
     *
     * @param roleInfo
     * @param integral
     */
    void addFlowCashIntegralRecord(RoleInfo roleInfo, Integral integral);

}
