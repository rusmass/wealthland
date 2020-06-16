package com.ascn.richlife.service.role;


import com.ascn.richlife.model.loan.LoanRecord;
import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;

/**
 * 借款记录
 *
 * Created by zhangpengxiang on 17/4/13.
 */
public interface RoleLoanRecordInfoService {

    /**
     * 初始化借款记录
     * @param roleLoanInfo
     * @return
     */
    RoleLoanRecordInfo init(RoleLoanInfo roleLoanInfo);

    /**
     * 增加借款记录
     * @param roleLoanRecordInfo
     * @param loanRecord
     */
    void addLoanRecord(RoleLoanRecordInfo roleLoanRecordInfo, LoanRecord loanRecord);
}
