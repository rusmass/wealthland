package com.ascn.richlife.service.role.impl;


import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.model.role.RoleDataManageInfo;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.spend.SpendInfo;
import com.ascn.richlife.service.role.RoleDataManageInfoService;
import com.ascn.richlife.service.role.RoleSpendInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色信息管理服务
 *
 * Created by Administrator on 2017/7/7 0007.
 */
@Service("roleDataManageInfoService")
public class RoleDataManageInfoServiceImpl implements RoleDataManageInfoService{

    @Autowired
    private RoleSpendInfoService roleSpendInfoService;

    //日志
    private Logger logger = Logger.getLogger(RoleDataManageInfoServiceImpl.class);

    public RoleDataManageInfo init(Role role) {

        RoleDataManageInfo roleDataManageInfo = new RoleDataManageInfo();

        //工资
        int wage = role.getWage();

        //住房贷款
        int houseLoan = role.getHouseLoan();

        //教育贷款
        int educationLoan = role.getEducationLoan();

        //购车贷款
        int carLoan = role.getCarLoan();

        //信用卡贷款
        int credit = role.getCreditCard();

        //额外贷款
        int additional = role.getAdditionalDebt();

        //总贷款
        int totalLoan = houseLoan + educationLoan + carLoan + credit + additional;

        //住房支出(利息)
        int houseInterest = role.getHouseInterest();

        //教育支出(利息)
        int educationInterest = role.getEducationInterest();

        //购车支出(利息)
        int carInterest = role.getCarInterest();

        //信用卡支出(利息)
        int creditInterest = role.getCreditCardInterest();

        //额外支出(利息)
        int additionalInterest = role.getAdditionalInterest();

        //其余支出
        int otherInterest = role.getOtherInterest();

        //税金
        int tax = role.getTax();

        //总支出
        int totalInterest = houseInterest + educationInterest + carInterest + creditInterest + additionalInterest + otherInterest + tax;

        //总收入
        int totalIncome = wage;

        //初始化年龄
        roleDataManageInfo.setAge(role.getAge());

        //初始化现金   初始化的现金 = 结算金额 = 总收入 - 总支出
        roleDataManageInfo.setCash(totalIncome - totalInterest);

        //初始化总收入 总收入 = 工资 + 非劳务收入(初始化非劳务收入为0)
        roleDataManageInfo.setTotalIncome(wage);

        //初始化总支出 总支出 = 银行贷款所产生的利息 + 角色所有的支出
        roleDataManageInfo.setTotalSpending(totalInterest);

        //初始化资产总额 资产总额 = 角色所有资产金额的总和
        roleDataManageInfo.setAssetTotalMoney(0);

        //初始化负债总额 负债总额 = 银行负债的总额 = 住房贷款 + 教育贷款 + 购车贷款 + 信用卡贷款 + 额外贷款
        roleDataManageInfo.setDebtTotalMoney(totalLoan);

        //初始化结算金额 结算金额 = 总收入 - 总支出
        roleDataManageInfo.setClosingDateMoney(totalIncome - totalInterest);

        //初始化非劳务收入
        roleDataManageInfo.setNonLaborIncome(0);

        //初始化流动现金
        roleDataManageInfo.setCashFlow(0);

        //初始化(内圈初始化的流动现金)
        roleDataManageInfo.setInitCashFlow(0);

        //初始化时间积分
        roleDataManageInfo.setTimeIntegral(0);

        //初始化品质积分
        roleDataManageInfo.setQualityIntegral(0);

        //初始化孩子的数量
        roleDataManageInfo.setHaveChildNumber(0);

        return roleDataManageInfo;
    }

    @Override
    public void innerInit(RoleDataManageInfo roleDataManageInfo) {

        //初始化结算金额
        roleDataManageInfo.setClosingDateMoney(roleDataManageInfo.getNonLaborIncome() * 100);

        //初始化现金
        roleDataManageInfo.setCash(roleDataManageInfo.getClosingDateMoney());

        //初始化资产总额
        roleDataManageInfo.setAssetTotalMoney(0);

        //初始化负债总额
        roleDataManageInfo.setDebtTotalMoney(0);

        //初始化总支出
        roleDataManageInfo.setTotalSpending(0);

        //初始化流动现金
        roleDataManageInfo.setCashFlow(0);

        //初始化初始流动现金
        roleDataManageInfo.setInitCashFlow(roleDataManageInfo.getClosingDateMoney());

        //初始化总收入
        roleDataManageInfo.setTotalIncome(roleDataManageInfo.getCashFlow() + roleDataManageInfo.getInitCashFlow());

        //初始化非劳务收入
        roleDataManageInfo.setNonLaborIncome(0);

        //初始化品质积分
        roleDataManageInfo.setQualityIntegral(roleDataManageInfo.getQualityIntegral() * 10);

        //初始化时间积分
        roleDataManageInfo.setTimeIntegral(roleDataManageInfo.getTimeIntegral() * 100);

        //初始化孩子数量
        roleDataManageInfo.setHaveChildNumber(0);

    }

    @Override
    public void updateRoleCash(RoleDataManageInfo roleDataManageInfo, int money) {

        roleDataManageInfo.setCash(roleDataManageInfo.getCash() + money);


    }


    @Override
    public void updateRoleAssetTotalMoney(RoleDataManageInfo roleDataManageInfo, int money) {

        roleDataManageInfo.setAssetTotalMoney(money);

    }

    @Override
    public void updateRoleDeptTotalMoney(RoleDataManageInfo roleDataManageInfo, int money) {

        roleDataManageInfo.setDebtTotalMoney(roleDataManageInfo.getDebtTotalMoney() + money);
    }

    @Override
    public void updateRoleTotalIncome(RoleInfo roleInfo, int money) {

        //获取数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //更新角色的总收入
        roleDataManageInfo.setTotalIncome(money);

        //更新结算金额
        updateRoleClosingDateMoney(roleInfo, roleDataManageInfo.getTotalIncome() - roleDataManageInfo.getTotalSpending());

    }

    @Override
    public void updateRoleTotalSpending(RoleInfo roleInfo, int money) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        logger.debug("总支出:" + roleDataManageInfo.getTotalSpending());

        logger.debug("金币:" + money);

        //更新总支出
        roleDataManageInfo.setTotalSpending(roleDataManageInfo.getTotalSpending() + money);

        logger.debug("更新后的总支出:" + roleDataManageInfo.getTotalSpending());

        //更新结算金额
        updateRoleClosingDateMoney(roleInfo, roleDataManageInfo.getTotalIncome() - roleDataManageInfo.getTotalSpending());

        logger.debug("更新后的总支出:" + roleDataManageInfo.getTotalSpending());

    }

    @Override
    public void updateRoleClosingDateMoney(RoleInfo roleInfo, int money) {

        //更新结算金额
        roleInfo.getRoleDataManageInfo().setClosingDateMoney(money);

        //获取角色贷款信息
//        RoleLoanInfo roleLoanInfo = role.getRoleLoanInfo();

//        //更新银行贷款上限
//        roleLoanInfoService.updateBankLoanLimit(roleLoanInfo, money * 10);
//
//        if (money * 10 - roleLoanInfo.getBankTotalLoan() < 0) {
//            //更新银行可贷款
//            roleLoanInfoService.updateBankCanLoan(roleLoanInfo, 0);
//        } else {
//            //更新银行可贷款
//            roleLoanInfoService.updateBankCanLoan(roleLoanInfo, money * 10 - roleLoanInfo.getBankTotalLoan());
//
//        }
//
//        //更新信用卡贷款上限
//        roleLoanInfoService.updateCreditLoanLimit(roleLoanInfo, money * 3);
//
//        if (money * 3 - roleLoanInfo.getCreditTotalLoan() < 0) {
//            //更新信用卡可贷款
//            roleLoanInfoService.updateCreditCanLoan(roleLoanInfo, 0);
//        } else {
//            //更新信用卡可贷款
//            roleLoanInfoService.updateCreditCanLoan(roleLoanInfo, money * 3 - roleLoanInfo.getCreditTotalLoan());
//        }
//
//        //更新总的贷款上限
//        roleLoanInfoService.updateTotalLoanLimit(roleLoanInfo, roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());
//
//        //更新总的可贷款
//        roleLoanInfoService.updateTotalCanLoan(roleLoanInfo, roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());

    }

    @Override
    public void updateRoleNonLaborIncome(RoleDataManageInfo roleDataManageInfo, int money) {

        roleDataManageInfo.setNonLaborIncome(money);

    }

    @Override
    public void updateRoleFlowCash(RoleInfo roleInfo, int money) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //更新流动资金
        roleDataManageInfo.setCashFlow(money);

        //更新结算金额
        updateRoleClosingDateMoney(roleInfo, roleDataManageInfo.getCashFlow() + roleDataManageInfo.getInitCashFlow());

    }

    @Override
    public void updateRoleQualityIntegral(RoleDataManageInfo roleDataManageInfo, int integral) {

        roleDataManageInfo.setQualityIntegral(integral);

    }

    @Override
    public void updateRoleTimeIntegral(RoleDataManageInfo roleDataManageInfo, int integral) {

        roleDataManageInfo.setTimeIntegral(integral);

    }

    @Override
    public void updateRoleHaveChildNumber(RoleInfo roleInfo) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //添加孩子数量
        roleDataManageInfo.setHaveChildNumber(roleDataManageInfo.getHaveChildNumber() + 1);

        SpendInfo spendInfo = new SpendInfo();

        spendInfo.setName("生孩子");
        spendInfo.setMoney(-roleInfo.getRoleBasicInfo().getGiveChildMoney());

        roleSpendInfoService.addBasicSpend(roleInfo,spendInfo);




    }
}
