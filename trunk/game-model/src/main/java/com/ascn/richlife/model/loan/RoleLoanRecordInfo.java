package com.ascn.richlife.model.loan;

import java.util.List;

/**
 * 角色贷款记录
 */
public class RoleLoanRecordInfo {

    /**
     * 角色贷款记录
     */
    private List<LoanRecord> loanRecords;

    public List<LoanRecord> getLoanRecords() {
        return loanRecords;
    }

    public RoleLoanRecordInfo setLoanRecords(List<LoanRecord> loanRecords) {
        this.loanRecords = loanRecords;
        return this;
    }
}
