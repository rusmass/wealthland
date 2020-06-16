package com.ascn.richlife.model.loan;

/**
 * 银行贷款信息
 */
public class RoleLoanInfo {

    /**
     * 银行贷款次数
     */
    private int bankLoanNumber;

    /**
     * 信用卡贷款次数
     */
    private int creditLoanNumber;

    /**
     * 银行可贷款
     */
    private int bankCanLoan;

    /**
     * 银行累计贷款
     */
    private int bankTotalLoan;

    /**
     * 银行累计利息
     */
    private int bankTotalInterest;

    /**
     * 银行贷款上限
     */
    private int bankLoanLimit;

    /**
     * 信用卡可贷款
     */
    private int creditCanLoan;

    /**
     * 信用卡累计贷款
     */
    private int creditTotalLoan;

    /**
     * 信用卡累计利息
     */
    private int creditTotalInterest;

    /**
     * 信用卡贷款上限
     */
    private int creditLoanLimit;

    /**
     * 总贷款上限
     */
    private int totalLoanLimit;

    /**
     * 总的可贷款
     */
    private int totalCanLoan;

    /**
     * 总贷款多少钱
     */
    private int totalLoan;

    public int getBankLoanNumber() {
        return bankLoanNumber;
    }

    public RoleLoanInfo setBankLoanNumber(int bankLoanNumber) {
        this.bankLoanNumber = bankLoanNumber;
        return this;
    }

    public int getCreditLoanNumber() {
        return creditLoanNumber;
    }

    public RoleLoanInfo setCreditLoanNumber(int creditLoanNumber) {
        this.creditLoanNumber = creditLoanNumber;
        return this;
    }

    public int getBankCanLoan() {
        return bankCanLoan;
    }

    public RoleLoanInfo setBankCanLoan(int bankCanLoan) {
        this.bankCanLoan = bankCanLoan;
        return this;
    }

    public int getBankTotalLoan() {
        return bankTotalLoan;
    }

    public RoleLoanInfo setBankTotalLoan(int bankTotalLoan) {
        this.bankTotalLoan = bankTotalLoan;
        return this;
    }

    public int getBankTotalInterest() {
        return bankTotalInterest;
    }

    public RoleLoanInfo setBankTotalInterest(int bankTotalInterest) {
        this.bankTotalInterest = bankTotalInterest;
        return this;
    }

    public int getBankLoanLimit() {
        return bankLoanLimit;
    }

    public RoleLoanInfo setBankLoanLimit(int bankLoanLimit) {
        this.bankLoanLimit = bankLoanLimit;
        return this;
    }

    public int getCreditCanLoan() {
        return creditCanLoan;
    }

    public RoleLoanInfo setCreditCanLoan(int creditCanLoan) {
        this.creditCanLoan = creditCanLoan;
        return this;
    }

    public int getCreditTotalLoan() {
        return creditTotalLoan;
    }

    public RoleLoanInfo setCreditTotalLoan(int creditTotalLoan) {
        this.creditTotalLoan = creditTotalLoan;
        return this;
    }

    public int getCreditTotalInterest() {
        return creditTotalInterest;
    }

    public RoleLoanInfo setCreditTotalInterest(int creditTotalInterest) {
        this.creditTotalInterest = creditTotalInterest;
        return this;
    }

    public int getCreditLoanLimit() {
        return creditLoanLimit;
    }

    public RoleLoanInfo setCreditLoanLimit(int creditLoanLimit) {
        this.creditLoanLimit = creditLoanLimit;
        return this;
    }

    public int getTotalLoanLimit() {
        return totalLoanLimit;
    }

    public RoleLoanInfo setTotalLoanLimit(int totalLoanLimit) {
        this.totalLoanLimit = totalLoanLimit;
        return this;
    }

    public int getTotalCanLoan() {
        return totalCanLoan;
    }

    public RoleLoanInfo setTotalCanLoan(int totalCanLoan) {
        this.totalCanLoan = totalCanLoan;
        return this;
    }

    public int getTotalLoan() {
        return totalLoan;
    }

    public RoleLoanInfo setTotalLoan(int totalLoan) {
        this.totalLoan = totalLoan;
        return this;
    }
}
