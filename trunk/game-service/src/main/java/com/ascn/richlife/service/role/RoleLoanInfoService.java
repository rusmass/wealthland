package com.ascn.richlife.service.role;


import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.role.RoleDataManageInfo;
import com.ascn.richlife.model.role.RoleInfo;

/**
 * 角色借贷信息服务
 *
 * Created by zhangpengxiang on 17/4/14.
 */
public interface RoleLoanInfoService {

    /**
     * 初始化角色借贷信息
     *
     * @param roleDataManageInfo
     * @return
     */
    RoleLoanInfo init(RoleDataManageInfo roleDataManageInfo);

    /**
     * 初始化内圈借贷信息
     *
     * @param roleInfo
     */
    void innerInit(RoleInfo roleInfo);

    /**
     * 借款
     *
     * @param roleInfo
     * @param bankMoney
     * @param creditMoney
     * @param outOrIn
     */
    void loan(RoleInfo roleInfo, int bankMoney, int creditMoney, int outOrIn);

    /**
     * 还款
     *
     * @param roleInfo
     * @param repayType
     * @param repayName
     */
    int repay(RoleInfo roleInfo, int repayType, String repayName, int outOrIn);

    /**
     * 更新银行可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateBankCanLoan(RoleLoanInfo roleLoanInfo, int money);

    /**
     * 更新信用卡可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateCreditCanLoan(RoleLoanInfo roleLoanInfo, int money);

    /**
     * 更新银行贷款上限
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateBankLoanLimit(RoleLoanInfo roleLoanInfo, int money);

    /**
     * 更新信用卡贷款上限
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateCreditLoanLimit(RoleLoanInfo roleLoanInfo, int money);

    /**
     * 更新总贷款上限
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateTotalLoanLimit(RoleLoanInfo roleLoanInfo, int money);

    /**
     * 更新总的可贷款
     *
     * @param roleLoanInfo
     * @param money
     */
    void updateTotalCanLoan(RoleLoanInfo roleLoanInfo, int money);
}
