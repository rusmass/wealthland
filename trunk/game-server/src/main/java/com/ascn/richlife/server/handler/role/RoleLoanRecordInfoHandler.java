package com.ascn.richlife.server.handler.role;

import com.ascn.richlife.model.loan.LoanRecord;
import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.loan.RoleLoanRecordInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色贷款信息管理
 *
 * Created by zhangpengxiang on 17/4/14.
 */
@Component("roleLoanRecordInfoHandler")
public class RoleLoanRecordInfoHandler {

    public RoleLoanRecordInfo init(RoleLoanInfo roleLoanInfo) {

        RoleLoanRecordInfo roleLoanRecordInfo = new RoleLoanRecordInfo();

        List<LoanRecord> loanRecordList = new ArrayList<LoanRecord>();

//        LoanRecord loanRecord = new LoanRecord();
//
//        loanRecord.setBankLoan(500);
//        loanRecord.setBankInterest(50);
//
//        loanRecordList.add(loanRecord);
//
//        LoanRecord loanRecord1 = new LoanRecord();
//        loanRecord1.setBankLoan(5000);
//        loanRecord1.setBankInterest(500);
//        loanRecord1.setCreditLoan(1000);
//        loanRecord.setCreditInterest(300);

        //loanRecordList.add(loanRecord1);



        roleLoanRecordInfo.setLoanRecords(loanRecordList);

        return roleLoanRecordInfo;
    }

    public void addLoanRecord(RoleLoanRecordInfo roleLoanRecordInfo, LoanRecord loanRecord) {
        roleLoanRecordInfo.getLoanRecords().add(loanRecord);
    }
}
