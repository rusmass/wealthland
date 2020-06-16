package com.ascn.richlife.model.loan;

/**
 * 角色贷款记录
 */
public class LoanRecord {

    /**
     * 银行贷款
     */
    private int bankLoan;

    /**
     * 银行贷款利息
     */
    private int bankInterest;

    /**
     * 银行贷款的利率
     */
    private String bankInterestRate;

    /**
     * 信用卡贷款
     */
    private int creditLoan;

    /**
     * 信用卡利息
     */
    private int creditInterest;

    /**
     * 信用卡贷款的利率
     */
    private String creditInterestRate;

    /**
     * 贷款总额
     */
    private int loanMoney;


    public LoanRecord() {
        bankInterestRate = "10%";

        creditInterestRate = "3%";
    }

    public int getBankLoan() {
        return bankLoan;
    }

    public LoanRecord setBankLoan(int bankLoan) {
        this.bankLoan = bankLoan;
        return this;
    }

    public int getBankInterest() {
        return bankInterest;
    }

    public LoanRecord setBankInterest(int bankInterest) {
        this.bankInterest = bankInterest;
        return this;
    }

    public String getBankInterestRate() {
        return bankInterestRate;
    }

    public LoanRecord setBankInterestRate(String bankInterestRate) {
        this.bankInterestRate = bankInterestRate;
        return this;
    }

    public int getCreditLoan() {
        return creditLoan;
    }

    public LoanRecord setCreditLoan(int creditLoan) {
        this.creditLoan = creditLoan;
        return this;
    }

    public int getCreditInterest() {
        return creditInterest;
    }

    public LoanRecord setCreditInterest(int creditInterest) {
        this.creditInterest = creditInterest;
        return this;
    }

    public String getCreditInterestRate() {
        return creditInterestRate;
    }

    public LoanRecord setCreditInterestRate(String creditInterestRate) {
        this.creditInterestRate = creditInterestRate;
        return this;
    }

    public int getLoanMoney() {
        return loanMoney;
    }

    public LoanRecord setLoanMoney(int loanMoney) {
        this.loanMoney = loanMoney;
        return this;
    }
}
