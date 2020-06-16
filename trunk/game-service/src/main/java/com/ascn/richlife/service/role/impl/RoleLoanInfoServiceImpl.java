package com.ascn.richlife.service.role.impl;


import com.ascn.richlife.model.debt.DebtInfo;
import com.ascn.richlife.model.loan.LoanRecord;
import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;
import com.ascn.richlife.model.role.RoleDataManageInfo;
import com.ascn.richlife.model.role.RoleDebtsInfo;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleSpendInfo;
import com.ascn.richlife.model.spend.SpendInfo;
import com.ascn.richlife.service.role.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 角色借贷信息
 *
 * Created by zhangpengxiang on 17/4/14.
 */
@Service("roleLoanInfoService")
public class RoleLoanInfoServiceImpl implements RoleLoanInfoService {

    private Logger logger = Logger.getLogger(RoleLoanInfoServiceImpl.class);

    @Autowired
    private RoleDataManageInfoService roleDataManageInfoService;

    @Autowired
    private RoleDebtsInfoService roleDebtsInfoService;

    @Autowired
    private RoleSpendInfoService roleSpendInfoService;

    @Autowired
    private RoleLoanRecordInfoService roleLoanRecordInfoService;

    @Override
    public RoleLoanInfo init(RoleDataManageInfo roleDataManageInfo) {

        //获取结算金额
        int closingDateMoney = roleDataManageInfo.getClosingDateMoney();

        //初始化角色贷款信息
        RoleLoanInfo roleLoanInfo = new RoleLoanInfo();

        //初始化银行贷款次数
        roleLoanInfo.setBankLoanNumber(0);

        //初始化信用卡贷款次数
        roleLoanInfo.setCreditLoanNumber(0);

//        //初始化银行已经贷了多少钱
//        roleLoanInfo.setBankLoan(0);
//
//        //初始化信用卡贷了多少钱
//        roleLoanInfo.setCreditLoan(0);

        /*
        初始化银行贷款信息
        银行贷款上限为:结算金额的10倍
        银行可以贷款 = 贷款上限 - 已贷款
        银行累积贷款 = 0
        银行累积利息 = 0
         */

        //银行贷款上限
        roleLoanInfo.setBankLoanLimit(closingDateMoney * 10);

        //银行可贷款
        roleLoanInfo.setBankCanLoan(closingDateMoney * 10);

        //银行累积贷款
        roleLoanInfo.setBankTotalInterest(0);

        //银行累积利息
        roleLoanInfo.setBankTotalLoan(0);

        /*
        初始化信用卡贷款信息
        信用卡贷款上限为:结算金额的3倍
        信用卡可以贷款 = 贷款上限 - 已贷款
        信用卡累积贷款 = 0
        信用卡累积利息 = 0
         */

        //信用卡贷款上限
        roleLoanInfo.setCreditLoanLimit(closingDateMoney * 3);

        //信用卡可贷款
        roleLoanInfo.setCreditCanLoan(closingDateMoney * 3);

        //信用卡累计贷款利息
        roleLoanInfo.setCreditTotalInterest(0);

        //信用卡累计贷款
        roleLoanInfo.setCreditTotalLoan(0);


        //总贷款上限
        roleLoanInfo.setTotalLoanLimit(roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());

        //总共可以贷款
        roleLoanInfo.setTotalCanLoan(roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());

        //总工贷款
        roleLoanInfo.setTotalLoan(roleLoanInfo.getBankTotalLoan() + roleLoanInfo.getCreditTotalLoan());

        return roleLoanInfo;
    }

    @Override
    public void innerInit(RoleInfo roleInfo) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色借贷信息
        RoleLoanInfo roleLoanInfo = roleInfo.getRoleLoanInfo();

        //获取结算金额
        int closingDateMoney = roleDataManageInfo.getClosingDateMoney();

        /*
        初始化银行贷款信息
        银行贷款上限为:结算金额的100倍
        银行可以贷款 = 贷款上限 - 已贷款
        银行累积贷款 = 0
        银行累积利息 = 0
         */

        //银行贷款上限
        roleLoanInfo.setBankLoanLimit(closingDateMoney * 100);

        //银行可贷款
        roleLoanInfo.setBankCanLoan(closingDateMoney * 100);

        //银行累积贷款
        roleLoanInfo.setBankTotalInterest(0);

        //银行累积利息
        roleLoanInfo.setBankTotalLoan(0);

        /*
        初始化信用卡贷款信息
        全部清空
         */

        //信用卡贷款上限
        roleLoanInfo.setCreditLoanLimit(0);

        //信用卡可贷款
        roleLoanInfo.setCreditCanLoan(0);

        //信用卡累计贷款利息
        roleLoanInfo.setCreditTotalInterest(0);

        //信用卡累计贷款
        roleLoanInfo.setCreditTotalLoan(0);


        //总贷款上限
        roleLoanInfo.setTotalLoanLimit(roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());

        //总共可以贷款
        roleLoanInfo.setTotalCanLoan(roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());

        //总工贷款
        roleLoanInfo.setTotalLoan(roleLoanInfo.getBankTotalLoan() + roleLoanInfo.getCreditTotalLoan());

    }

    @Override
    public void loan(RoleInfo roleInfo, int bankMoney, int creditMoney, int outOrIn) {

        //判断玩家是外圈还是内圈
        if (outOrIn == 0) {

            outerLoan(roleInfo, bankMoney, creditMoney);

        } else if (outOrIn == 1) {

            innerLoan(roleInfo, bankMoney);

        } else {
            logger.error("错误的内外圈标识");
        }

    }

    /**
     * 外圈借款
     *
     * @param roleInfo
     * @param bankMoney
     * @param creditMoney
     */
    private void outerLoan(RoleInfo roleInfo, int bankMoney, int creditMoney) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色贷款信息
        RoleLoanInfo roleLoanInfo = roleInfo.getRoleLoanInfo();

        //获取角色借款记录
        RoleLoanRecordInfo roleLoanRecordInfo = roleInfo.getRoleLoanRecordInfo();

        //获取结算金额
        int closingDateMoney = roleDataManageInfo.getClosingDateMoney();

        /*
        贷款触发事件:
        1.更新角色的金币
        2.更新角色贷款的数据
        3.增加贷款记录
        4.增加角色负债记录
        5.增加角色支出
         */

        //获取借款总金额
        int totalMoney = bankMoney + creditMoney;

        //更新角色的金币
        roleDataManageInfoService.updateRoleCash(roleDataManageInfo, totalMoney);

        //更新角色贷款的数据

        //是否银行贷了款
        if (bankMoney > 0) {

            //判断玩家是否能贷这么多金币
            if (bankMoney <= roleLoanInfo.getBankCanLoan()) {

                //更新银行贷款数据
                updateBankLoanInfo(roleLoanInfo, bankMoney);

                //为本次银行借款命名
                String bankLoanName = "银行贷款" + roleLoanInfo.getBankLoanNumber();

                //本次借款的利息
                int bankLoanInterest = (int) (bankMoney * 0.1);

                //新建负债
                DebtInfo bankLoanDept = new DebtInfo(bankLoanName, bankMoney, bankLoanInterest);

                //增加负债
                roleDebtsInfoService.addNewDebt(roleInfo, bankLoanDept);

                //新建支出
                SpendInfo bankLoanSpend = new SpendInfo(bankLoanName, -bankLoanInterest);

                //增加支出
                roleSpendInfoService.addNewSpend(roleInfo, bankLoanSpend);

                //更新银行贷款上限
                updateBankLoanLimit(roleLoanInfo, closingDateMoney * 10);

                if (closingDateMoney * 10 - roleLoanInfo.getBankTotalLoan() < 0) {

                    //更新银行可贷款
                    updateBankCanLoan(roleLoanInfo, 0);
                } else {

                    //更新银行可贷款
                    updateBankCanLoan(roleLoanInfo, closingDateMoney * 10 - roleLoanInfo.getBankTotalLoan());

                }

            } else {
                //贷款失败,银行贷款不可以贷这么多
                logger.debug("贷款失败,银行贷款不可以贷这么多");
                return;
            }
        }

        //是否信用卡贷了款
        if (creditMoney > 0) {

            //判断玩家是否能贷这么多金币
            if (creditMoney <= roleLoanInfo.getCreditCanLoan()) {

                //更新信用卡贷款数据
                updateCreditLoanInfo(roleLoanInfo, creditMoney);

                //为本次信用卡借款命名
                String creditLoanName = "信用卡透支" + roleLoanInfo.getCreditLoanNumber();

                //本次借款的利息
                int creditLoanInterest = (int) (creditMoney * 0.03);

                //新建负债
                DebtInfo bankLoanDept = new DebtInfo(creditLoanName, creditMoney, creditLoanInterest);

                //增加负债
                roleDebtsInfoService.addNewDebt(roleInfo, bankLoanDept);

                //新建支出
                SpendInfo creditLoanSpend = new SpendInfo(creditLoanName, -creditLoanInterest);

                //增加支出
                roleSpendInfoService.addNewSpend(roleInfo, creditLoanSpend);


                //更新信用卡贷款上限
                updateCreditLoanLimit(roleLoanInfo, closingDateMoney * 3);

                if (closingDateMoney * 3 - roleLoanInfo.getCreditTotalLoan() < 0) {
                    //更新信用卡可贷款
                    updateCreditCanLoan(roleLoanInfo, 0);
                } else {
                    //更新信用卡可贷款
                    updateCreditCanLoan(roleLoanInfo, closingDateMoney * 3 - roleLoanInfo.getCreditTotalLoan());
                }

            } else {
                //贷款失败,信用卡贷款不可以贷这么多
                logger.debug("贷款失败,信用卡贷款不可以贷这么多");
                return;
            }
        }

        //更新总的贷款上限
        updateTotalLoanLimit(roleLoanInfo, roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());

        //更新总的可贷款
        updateTotalCanLoan(roleLoanInfo, roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());


        //增加贷款记录
        LoanRecord loanRecord = new LoanRecord();

        loanRecord.setBankLoan(bankMoney);
        loanRecord.setBankInterest((int) (bankMoney * 0.1));

        loanRecord.setCreditLoan(creditMoney);
        loanRecord.setCreditInterest((int) (creditMoney * 0.03));

        roleLoanRecordInfoService.addLoanRecord(roleLoanRecordInfo, loanRecord);
    }

    /**
     * 内圈借款
     *
     * @param roleInfo
     * @param bankMoney
     */
    private void innerLoan(RoleInfo roleInfo, int bankMoney) {

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色贷款信息
        RoleLoanInfo roleLoanInfo = roleInfo.getRoleLoanInfo();

        //获取角色借款记录
        RoleLoanRecordInfo roleLoanRecordInfo = roleInfo.getRoleLoanRecordInfo();

        //获取结算金额
        int closingDateMoney = roleDataManageInfo.getClosingDateMoney();

         /*
        贷款触发事件:
        1.更新角色的金币
        2.更新角色贷款的数据
        3.增加贷款记录
        4.增加角色负债记录
        5.增加角色支出
         */

        //获取借款总金额
        int totalMoney = bankMoney;

        //更新角色的金币
        roleDataManageInfoService.updateRoleCash(roleDataManageInfo, totalMoney);

        //判断玩家是否能贷这么多金币
        if (bankMoney <= roleLoanInfo.getBankCanLoan()) {

            //更新银行贷款数据
            updateBankLoanInfo(roleLoanInfo, bankMoney);

            //为本次银行借款命名
            String bankLoanName = "银行贷款" + roleLoanInfo.getBankLoanNumber();

            //本次借款的利息
            int bankLoanInterest = (int) (bankMoney * 0.01);

            //新建负债
            DebtInfo bankLoanDept = new DebtInfo(bankLoanName, bankMoney, bankLoanInterest);

            //增加负债
            roleDebtsInfoService.addNewDebt(roleInfo, bankLoanDept);

            //新建支出
            SpendInfo bankLoanSpend = new SpendInfo(bankLoanName, -bankLoanInterest);

            //增加支出
            roleSpendInfoService.addNewSpend(roleInfo, bankLoanSpend);

            //更新银行贷款上限
            updateBankLoanLimit(roleLoanInfo, closingDateMoney * 100);

            if (closingDateMoney * 100 - roleLoanInfo.getBankTotalLoan() < 0) {

                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, 0);

            } else {

                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, closingDateMoney * 100 - roleLoanInfo.getBankTotalLoan());

            }

        } else {
            //贷款失败,银行贷款不可以贷这么多
            logger.debug("贷款失败,银行贷款不可以贷这么多");
            return;
        }

        //更新总的贷款上限
        updateTotalLoanLimit(roleLoanInfo, roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());

        //更新总的可贷款
        updateTotalCanLoan(roleLoanInfo, roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());

        //增加贷款记录
        LoanRecord loanRecord = new LoanRecord();

        loanRecord.setBankLoan(bankMoney);
        loanRecord.setBankInterest((int) (bankMoney * 0.01));


        roleLoanRecordInfoService.addLoanRecord(roleLoanRecordInfo, loanRecord);

    }

    @Override
    public int repay(RoleInfo roleInfo, int repayType, String repayName, int outOrIn) {

        int status = 0;

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色负债信息
        RoleDebtsInfo roleDebtsInfo = roleInfo.getRoleDebtsInfo();

        //获取角色支出信息
        RoleSpendInfo roleSpendInfo = roleInfo.getRoleSpendInfo();

        //判断还的是基础负债还是新增负债 0 基础 1 新增
        if (repayType == 0) {

            //获取基础负债
            Map<String, DebtInfo> basicDebt = roleDebtsInfo.getBasicDebt();

            //判断是否存在这个负债
            if (basicDebt.containsKey(repayName)) {

                //获取此负债
                DebtInfo debtInfo = basicDebt.get(repayName);

                //获取负债金额
                int money = debtInfo.getDebtMoney();

                //更新玩家金币
                roleDataManageInfoService.updateRoleCash(roleDataManageInfo, -money);

                //删除此负债
                roleDebtsInfoService.removeBasicDebt(roleDebtsInfo, repayName);

                //判断是否存在此支出
                if (roleSpendInfo.getBasicSpend().containsKey(repayName)) {

                    //删除此支出
                    roleSpendInfoService.removeBasicSpend(roleInfo, repayName);

                }


            } else {
                //还款失败,基础负债不存在此负债
                status = -1;
                logger.debug("还款失败,基础负债不存在此负债");
                return status;
            }


        } else if (repayType == 1) {

            //获取新增负债
            Map<String, DebtInfo> addNewDebt = roleDebtsInfo.getAddNewDebt();

            //判断是否存在这个负债
            if (addNewDebt.containsKey(repayName)) {

                //获取此负债
                DebtInfo debtInfo = addNewDebt.get(repayName);

                //获取负债金额
                int money = debtInfo.getDebtMoney();

                //更新玩家金币
                roleDataManageInfoService.updateRoleCash(roleDataManageInfo, -money);

                //删除此负债
                roleDebtsInfoService.removeNewDebt(roleDebtsInfo, repayName);

                //判断是否存在此支出
                if (roleSpendInfo.getAddNewSpend().containsKey(repayName)) {

                    //删除此支出
                    roleSpendInfoService.removeNewSpend(roleInfo, repayName);

                }

                //判断还的是银行负债还是信用卡负债
                if (repayName.indexOf("银行贷款") != -1) {

                    updateBankRepayInfo(roleInfo.getRoleLoanInfo(), -money);

                } else if (repayName.indexOf("信用卡透支") != -1) {

                    updateCreditRepayInfo(roleInfo.getRoleLoanInfo(), -money);
                }


            } else {
                //还款失败,基础负债不存在此负债
                status = -2;
                logger.debug("还款失败,新增负债不存在此负债");
                return status;
            }


        } else {
            logger.error("未知的还款类型");
        }

        //获取角色贷款信息
        RoleLoanInfo roleLoanInfo = roleInfo.getRoleLoanInfo();

        //获取结算金额
        int closingDateMoney = roleDataManageInfo.getClosingDateMoney();

        if (outOrIn == 0) {

            //更新银行贷款上限
            updateBankLoanLimit(roleLoanInfo, closingDateMoney * 10);

            if (closingDateMoney * 10 - roleLoanInfo.getBankTotalLoan() < 0) {
                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, 0);
            } else {
                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, closingDateMoney * 10 - roleLoanInfo.getBankTotalLoan());

            }

            //更新信用卡贷款上限
            updateCreditLoanLimit(roleLoanInfo, closingDateMoney * 3);

            if (closingDateMoney * 3 - roleLoanInfo.getCreditTotalLoan() < 0) {
                //更新信用卡可贷款
                updateCreditCanLoan(roleLoanInfo, 0);
            } else {
                //更新信用卡可贷款
                updateCreditCanLoan(roleLoanInfo, closingDateMoney * 3 - roleLoanInfo.getCreditTotalLoan());
            }

            //更新总的贷款上限
            updateTotalLoanLimit(roleLoanInfo, roleLoanInfo.getBankLoanLimit() + roleLoanInfo.getCreditLoanLimit());

            //更新总的可贷款
            updateTotalCanLoan(roleLoanInfo, roleLoanInfo.getBankCanLoan() + roleLoanInfo.getCreditCanLoan());

        } else {

            //更新银行贷款上限
            updateBankLoanLimit(roleLoanInfo, closingDateMoney * 100);

            if (closingDateMoney * 100 - roleLoanInfo.getBankTotalLoan() < 0) {
                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, 0);
            } else {
                //更新银行可贷款
                updateBankCanLoan(roleLoanInfo, closingDateMoney * 100 - roleLoanInfo.getBankTotalLoan());

            }
        }

        return 0;
    }

    /**
     * 更新银行贷款次数
     *
     * @param roleLoanInfo
     */
    private void updateBankLoanNumber(RoleLoanInfo roleLoanInfo) {
        roleLoanInfo.setBankLoanNumber(roleLoanInfo.getBankLoanNumber() + 1);
    }

    /**
     * 更新信用卡贷款次数
     *
     * @param roleLoanInfo
     */
    private void updateCreditLoanNumber(RoleLoanInfo roleLoanInfo) {
        roleLoanInfo.setCreditLoanNumber(roleLoanInfo.getCreditLoanNumber() + 1);
    }

    /**
     * 更新银行总贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateBankTotalLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setBankTotalLoan(roleLoanInfo.getBankTotalLoan() + money);
    }

    /**
     * 更新信用卡总贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateCreditTotalLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setCreditTotalLoan(roleLoanInfo.getCreditTotalLoan() + money);

    }

    /**
     * 更新银行贷款总利息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateBankTotalInterest(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setBankTotalInterest((int) (roleLoanInfo.getBankTotalInterest() + money * 0.1));
    }

    /**
     * 更新信用卡总利息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateCreditTotalInterest(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setCreditTotalInterest((int) (roleLoanInfo.getCreditTotalInterest() + money * 0.03));
    }

    /**
     * 更新银行可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    public void updateBankCanLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setBankCanLoan(money);
    }

    /**
     * 更新信用卡可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    public void updateCreditCanLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setCreditCanLoan(money);
    }

    @Override
    public void updateBankLoanLimit(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setBankLoanLimit(money);
    }

    @Override
    public void updateCreditLoanLimit(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setCreditLoanLimit(money);
    }

    @Override
    public void updateTotalLoanLimit(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setTotalLoanLimit(money);
    }

    /**
     * 更新总贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateTotalLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setTotalLoan(roleLoanInfo.getTotalLoan() + money);
    }

    /**
     * 更新总的可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    public void updateTotalCanLoan(RoleLoanInfo roleLoanInfo, int money) {
        roleLoanInfo.setTotalCanLoan(money);
    }

    /**
     * 更新银行信息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateBankLoanInfo(RoleLoanInfo roleLoanInfo, int money) {

        //更新银行贷款的次数
        updateBankLoanNumber(roleLoanInfo);

        //增加银行累积贷款
        updateBankTotalLoan(roleLoanInfo, money);

        //增加银行贷款累计利息
        updateBankTotalInterest(roleLoanInfo, money);

        //更新银行可贷款
        //updateBankCanLoan(roleLoanInfo, money);

        //更新总贷款
        updateTotalLoan(roleLoanInfo, money);

        //更新总的可贷款
        //updateTotalCanLoan(roleLoanInfo, money);
    }

    /**
     * 更新信用卡信息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateCreditLoanInfo(RoleLoanInfo roleLoanInfo, int money) {

        //更新信用卡贷款的次数
        updateCreditLoanNumber(roleLoanInfo);

        //增加信用卡累积贷款
        updateCreditTotalLoan(roleLoanInfo, money);

        //增加信用卡贷款累计利息
        updateCreditTotalInterest(roleLoanInfo, money);

        //更新信用卡可贷款
        //updateCreditCanLoan(roleLoanInfo, money);

        //更新总贷款
        updateTotalLoan(roleLoanInfo, money);

        //更新总的可贷款
        //updateTotalCanLoan(roleLoanInfo, money);
    }

    /**
     * 更新银行还款信息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateBankRepayInfo(RoleLoanInfo roleLoanInfo, int money) {

        //更新银行累计贷款
        updateBankTotalLoan(roleLoanInfo, money);

        //更新银行累积利息
        updateBankTotalInterest(roleLoanInfo, money);

        //更新总贷款
        updateTotalLoan(roleLoanInfo, money);
    }

    /**
     * 更新信用卡还款信息
     *
     * @param roleLoanInfo
     * @param money
     */
    private void updateCreditRepayInfo(RoleLoanInfo roleLoanInfo, int money) {

        //更新信用卡累计贷款
        updateCreditTotalLoan(roleLoanInfo, money);

        //更新信用卡累计利息
        updateCreditTotalInterest(roleLoanInfo, money);

        //更新总贷款
        updateTotalLoan(roleLoanInfo, money);


    }


}
