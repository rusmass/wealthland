package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.card.OuterBigChance;
import com.ascn.richlife.model.card.OuterSmallChance;
import com.ascn.richlife.model.card.OuterStock;
import com.ascn.richlife.model.income.NonLaborIncome;
import com.ascn.richlife.model.integral.Integral;
import com.ascn.richlife.model.role.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色拥有的资产信息
 *
 * Created by zhangpengxiang on 17/4/10.
 */
@Component("roleHaveAssetInfoHandler")
public class RoleHaveAssetInfoHandler {

    private Logger logger = Logger.getLogger(RoleHaveAssetInfoHandler.class);

    @Autowired
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    @Autowired
    private RoleIncomeInfoHandler roleIncomeInfoHandler;

    @Autowired
    private RoleIntegralRecordHandler roleIntegralRecordHandler;

    @Autowired
    private RoleSellAssetRecordHandler roleSellAssetRecordHandler;

    public RoleHaveAssetInfo init(Role role) {

        RoleHaveAssetInfo roleHaveAssetInfo = new RoleHaveAssetInfo();

        List<OuterSmallChance> smallChances = new ArrayList<OuterSmallChance>();

        List<OuterStock> stocks = new ArrayList<OuterStock>();

        List<OuterBigChance> bigChances = new ArrayList<OuterBigChance>();

        //初始化拥有的小机会资产
        roleHaveAssetInfo.setSmallChances(smallChances);

        //初始化拥有的股票资产
        roleHaveAssetInfo.setStocks(stocks);

        //初始化拥有的大机会资产
        roleHaveAssetInfo.setBigChances(bigChances);

        //初始化资产总金额
        roleHaveAssetInfo.setAssetTotalMoney(0);

        return roleHaveAssetInfo;
    }

    public void addSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance) {

        //资产的成本
        int cost = Integer.parseInt(outerSmallChance.getCost());

        //非劳务收入
        int nonLaborIncome = outerSmallChance.getNonLaborIncome();

        //积分类型
        int integralType = outerSmallChance.getIntegralType();

        //积分数量
        int integralNumber = outerSmallChance.getIntegralNumber();

        //获取角色的资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //添加资产
        roleHaveAssetInfo.getSmallChances().add(outerSmallChance);

        //获取角色的数据
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //购买资产所花费的金币
        int spendMoney = outerSmallChance.getDownPayment();

        //扣掉角色购买资产所花费的金币
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

        //更新资产总额
        roleHaveAssetInfo.setAssetTotalMoney(roleHaveAssetInfo.getAssetTotalMoney() + cost);

        //更新角色数据里面的资产总额
        roleDataManageInfoHandler.updateRoleAssetTotalMoney(roleDataManageInfo, roleHaveAssetInfo.getAssetTotalMoney());

        if (nonLaborIncome > 0) {

            //更新玩家非劳务收入信息
            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerSmallChance.getId());
            income.setName(outerSmallChance.getName());
            income.setMoney(nonLaborIncome);

            roleIncomeInfoHandler.addNonLaborIncome(roleInfo, income);

        }

        //更新角色积分

        //1.时间积分   2.品质积分
        if (integralType == 1) {

            Integral integral = new Integral();
            integral.setName(outerSmallChance.getName());
            integral.setIntegral(integralNumber);

            roleIntegralRecordHandler.addTimeIntegralRecord(roleInfo, integral);

        } else if (integralType == 2) {

            Integral integral = new Integral();
            integral.setName(outerSmallChance.getName());
            integral.setIntegral(integralNumber);

            roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);

        }

    }

    public void addStock(RoleInfo roleInfo, OuterStock outerStock, int number) {

        List<OuterStock> outerStockList = roleInfo.getRoleHaveAssetInfo().getStocks();

        //判断资产列表是否有需要添加的资产
        boolean ifExist = false;

        for (OuterStock stock : outerStockList) {

            //如果有该资产,则累计数量
            if (stock.getId() == outerStock.getId()) {

                stock.setStockNumber(stock.getStockNumber() + number);

                ifExist = true;

            }

        }

        //如果没有则添加
        if (!ifExist) {
            outerStock.setStockNumber(number);
            outerStockList.add(outerStock);
        }

        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //购买股票所花费的金币
        int spendingMoney = outerStock.getTodayPrice() * number;

        //更新玩家的金钱
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendingMoney);

        //获取角色资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //更新资产总额
        roleHaveAssetInfo.setAssetTotalMoney(roleHaveAssetInfo.getAssetTotalMoney() + Math.abs(outerStock.getTodayPrice()) * number);

        //更新数据信息资产总额
        roleDataManageInfoHandler.updateRoleAssetTotalMoney(roleDataManageInfo, roleHaveAssetInfo.getAssetTotalMoney());

        //品质积分
        int qualityIntegral = outerStock.getQualityIntegral();

        //非劳务收入
        int nonLaborIncome = outerStock.getNonLaborIncome();

        //更新品质积分
        //roleDataManageInfoService.updateRoleQualityIntegral(roleDataManageInfo, qualityIntegral);

        if (nonLaborIncome > 0) {

            //更新玩家非劳务收入信息
            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerStock.getId());
            income.setName(outerStock.getName());
            income.setMoney(nonLaborIncome);

            roleIncomeInfoHandler.addNonLaborIncome(roleInfo, income);

        }

        if (qualityIntegral > 0) {

            //更新角色积分
            Integral integral = new Integral();
            integral.setName(outerStock.getName());
            integral.setIntegral(qualityIntegral);

            //添加积分记录
            roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);


        }

    }

    public void addBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance) {

        //资产的成本
        int cost = Integer.parseInt(outerBigChance.getCost());

        //非劳务收入
        int nonLaborIncome = outerBigChance.getNonLaborIncome();

        //获取角色的资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //添加资产
        roleHaveAssetInfo.getBigChances().add(outerBigChance);

        //获取角色的数据
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //购买资产所花费的金币
        int spendMoney = outerBigChance.getDownPayment();

        //扣掉角色购买资产所花费的金币
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

        //更新资产总额
        roleHaveAssetInfo.setAssetTotalMoney(roleHaveAssetInfo.getAssetTotalMoney() + cost);

        //更新角色数据里面的资产总额
        roleDataManageInfoHandler.updateRoleAssetTotalMoney(roleDataManageInfo, roleHaveAssetInfo.getAssetTotalMoney());

        if (nonLaborIncome > 0) {

            //更新玩家非劳务收入信息
            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerBigChance.getId());
            income.setName(outerBigChance.getName());
            income.setMoney(nonLaborIncome);

            roleIncomeInfoHandler.addNonLaborIncome(roleInfo, income);

        }
    }

    public void removeSmallChanceAsset(RoleInfo roleInfo, OuterSmallChance outerSmallChance, int sellPrice) {

        //获取资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //获取小机会资产列表
        List<OuterSmallChance> smallChanceList = roleHaveAssetInfo.getSmallChances();

        //是否存在该资产
        if (smallChanceList.contains(outerSmallChance)) {
            //移除资产
            for (int i = 0; i < smallChanceList.size(); i++) {
                if (smallChanceList.get(i).getId() == outerSmallChance.getId()) {
                    smallChanceList.remove(i);
                }
            }
        } else {
            logger.error("删除失败,没有这个小机会资产");
            return;
        }

        //新建资产出售记录
        RoleSellAssetInfo roleSellAssetInfo = new RoleSellAssetInfo();

        //资产名称
        roleSellAssetInfo.setName(outerSmallChance.getName());

        //单价
        roleSellAssetInfo.setPrice(Integer.parseInt(outerSmallChance.getCost()));

        //数量
        roleSellAssetInfo.setNumber(outerSmallChance.getNumber());

        //抵押贷款
        roleSellAssetInfo.setLoan(outerSmallChance.getMortgageLoan());

        //卖价
        roleSellAssetInfo.setSellPrice(sellPrice);

        //获取非劳务收入
        int nonLaborIncome = outerSmallChance.getNonLaborIncome();

        //非劳务收入
        roleSellAssetInfo.setNonLaborIncome(nonLaborIncome);

        //是否有非劳务收入
        if (nonLaborIncome > 0) {

            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerSmallChance.getId());
            income.setName(outerSmallChance.getName());
            income.setMoney(nonLaborIncome);

            //删除非劳务收入
            roleIncomeInfoHandler.removeNonLaborIncome(roleInfo, income);
        }

        //积分类型
        int integralType = outerSmallChance.getIntegralType();

        //积分数量
        int integralNumber = outerSmallChance.getIntegralNumber();

        //更新角色积分

        //1.时间积分   2.品质积分
        if (integralType == 1) {

            Integral integral = new Integral();
            integral.setName(outerSmallChance.getName());
            integral.setIntegral(-integralNumber);

            roleIntegralRecordHandler.addTimeIntegralRecord(roleInfo, integral);

        } else if (integralType == 2) {

            Integral integral = new Integral();
            integral.setName(outerSmallChance.getName());
            integral.setIntegral(-integralNumber);

            roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);

        }

        //获取净赚
        int net = sellPrice * outerSmallChance.getNumber() + outerSmallChance.getMortgageLoan();

        //净赚
        roleSellAssetInfo.setNet(net);

        //增加出售记录
        roleSellAssetRecordHandler.addRoleSellAssetRecord(roleInfo.getRoleSellAssetRecord(), roleSellAssetInfo);

        //更新玩家的金钱
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), net);

    }

    public void removeStock(RoleInfo roleInfo, OuterStock outerStock, int number, int sellPrice) {

        //获取资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //获取股票资产列表
        List<OuterStock> stockList = roleHaveAssetInfo.getStocks();

        //判断资产列表是否有这个股票
        boolean ifExist = false;

        //买股票时候的价钱
        int buyPrice = 0;

        //判断是否存在该资产
        for (int i = 0; i < stockList.size(); i++) {

            if (stockList.get(i).getId() == outerStock.getId()) {

                buyPrice = stockList.get(i).getTodayPrice();

                //判断卖了多少数量
                if (stockList.get(i).getStockNumber() == number) {

                    stockList.remove(i);

                } else if (stockList.get(i).getStockNumber() < number) {

                    logger.error("没有这么多的股票");
                    return;

                } else if (stockList.get(i).getStockNumber() > number) {

                    stockList.get(i).setStockNumber(stockList.get(i).getStockNumber() - number);

                }
                ifExist = true;
            }
        }

        if (!ifExist) {
            logger.error("没有这个股票");
            return;
        }

        //新建资产出售记录
        RoleSellAssetInfo roleSellAssetInfo = new RoleSellAssetInfo();

        //资产名称
        roleSellAssetInfo.setName(outerStock.getName());

        //单价
        roleSellAssetInfo.setPrice(Math.abs(buyPrice));

        //数量
        roleSellAssetInfo.setNumber(number);

        //抵押贷款
        roleSellAssetInfo.setLoan(0);

        //卖价
        roleSellAssetInfo.setSellPrice(sellPrice);

        //获取非劳务收入
        int nonLaborIncome = outerStock.getNonLaborIncome();

        //非劳务收入
        roleSellAssetInfo.setNonLaborIncome(nonLaborIncome);

        //是否有非劳务收入
        if (nonLaborIncome > 0) {

            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerStock.getId());
            income.setName(outerStock.getName());
            income.setMoney(nonLaborIncome);

            //删除非劳务收入
            roleIncomeInfoHandler.removeNonLaborIncome(roleInfo, income);
        }


        //更新角色积分

        //获取品质积分
        int qualityIntegral = outerStock.getQualityIntegral();

        if (qualityIntegral > 0) {
            Integral integral = new Integral();
            integral.setName(outerStock.getName());
            integral.setIntegral(-qualityIntegral);

            roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);
        }

        //获取净赚
        int net = sellPrice * number - Math.abs(buyPrice) * number;

        //净赚
        roleSellAssetInfo.setNet(net);

        //增加出售记录
        roleSellAssetRecordHandler.addRoleSellAssetRecord(roleInfo.getRoleSellAssetRecord(), roleSellAssetInfo);

        //更新玩家的金钱
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), sellPrice * number);
    }

    public void removeBigChanceAsset(RoleInfo roleInfo, OuterBigChance outerBigChance, int sellPrice) {

        //获取资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //获取大机会资产列表
        List<OuterBigChance> bigChanceList = roleHaveAssetInfo.getBigChances();

        //是否存在该资产
        if (bigChanceList.contains(outerBigChance)) {
            //移除资产
            for (int i = 0; i < bigChanceList.size(); i++) {
                if (bigChanceList.get(i).getId() == outerBigChance.getId()) {
                    bigChanceList.remove(i);
                }
            }
        } else {
            logger.error("删除失败,没有这个大机会资产");
            return;
        }

        //新建资产出售记录
        RoleSellAssetInfo roleSellAssetInfo = new RoleSellAssetInfo();

        //资产名称
        roleSellAssetInfo.setName(outerBigChance.getName());

        //单价
        roleSellAssetInfo.setPrice(Integer.parseInt(outerBigChance.getCost()));

        //数量
        roleSellAssetInfo.setNumber(outerBigChance.getNumber());

        //抵押贷款
        roleSellAssetInfo.setLoan(outerBigChance.getMortgageLoan());

        //卖价
        roleSellAssetInfo.setSellPrice(sellPrice);

        //获取非劳务收入
        int nonLaborIncome = outerBigChance.getNonLaborIncome();

        //非劳务收入
        roleSellAssetInfo.setNonLaborIncome(nonLaborIncome);

        //是否有非劳务收入
        if (nonLaborIncome > 0) {

            NonLaborIncome income = new NonLaborIncome();
            income.setId(outerBigChance.getId());
            income.setName(outerBigChance.getName());
            income.setMoney(nonLaborIncome);

            //删除非劳务收入
            roleIncomeInfoHandler.removeNonLaborIncome(roleInfo, income);
        }

        //获取净赚
        int net = sellPrice * outerBigChance.getNumber() + outerBigChance.getMortgageLoan();

        //净赚
        roleSellAssetInfo.setNet(net);

        //增加出售记录
        roleSellAssetRecordHandler.addRoleSellAssetRecord(roleInfo.getRoleSellAssetRecord(), roleSellAssetInfo);

        //更新玩家的金钱
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), net);

    }


}
