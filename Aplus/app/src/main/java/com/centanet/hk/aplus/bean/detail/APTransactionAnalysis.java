package com.centanet.hk.aplus.bean.detail;

public class APTransactionAnalysis {

    private int countIn30Days;             // 最近三十日成交宗數
    private int countIn30DaysCentaline;    // 最近三十日中原成交宗數
    private int countIn30DaysTelephone;    // 最近三十日有電話成交宗數
    private String transactionURL;         // 成交分析網頁

    public APTransactionAnalysis(int countIn30Days, int countIn30DaysCentaline, int countIn30DaysTelephone, String transactionURL) {
        this.countIn30Days = countIn30Days;
        this.countIn30DaysCentaline = countIn30DaysCentaline;
        this.countIn30DaysTelephone = countIn30DaysTelephone;
        this.transactionURL = transactionURL;
    }

    public void setCountIn30Days(int countIn30Days) {
        this.countIn30Days = countIn30Days;
    }

    public void setCountIn30DaysCentaline(int countIn30DaysCentaline) {
        this.countIn30DaysCentaline = countIn30DaysCentaline;
    }

    public void setCountIn30DaysTelephone(int countIn30DaysTelephone) {
        this.countIn30DaysTelephone = countIn30DaysTelephone;
    }

    public void setTransactionURL(String transactionURL) {
        this.transactionURL = transactionURL;
    }

    public int getCountIn30Days() {
        return countIn30Days;
    }

    public int getCountIn30DaysCentaline() {
        return countIn30DaysCentaline;
    }

    public int getCountIn30DaysTelephone() {
        return countIn30DaysTelephone;
    }

    public String getTransactionURL() {
        return transactionURL;
    }
}
