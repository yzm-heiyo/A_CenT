package com.centanet.hk.aplus.bean.detail;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class DetailDataInformation implements Serializable{

    private APTransactionAnalysis transactionAnalysis;
    private APDayBook daybook;
    private APCentaData centaData;

    public DetailDataInformation(APTransactionAnalysis transactionAnalysis, APDayBook daybook, APCentaData centaData) {
        transactionAnalysis = transactionAnalysis;
        daybook = daybook;
        centaData = centaData;
    }

    public void setTransactionAnalysis(APTransactionAnalysis transactionAnalysis) {
        transactionAnalysis = transactionAnalysis;
    }

    public void setDaybook(APDayBook daybook) {
        daybook = daybook;
    }

    public void setCentaData(APCentaData centaData) {
        centaData = centaData;
    }

    public APTransactionAnalysis getTransactionAnalysis() {
        return transactionAnalysis;
    }

    public APDayBook getDaybook() {
        return daybook;
    }

    public APCentaData getCentaData() {
        return centaData;
    }

    @Override
    public String toString() {
        return "DetailDataInformation{" +
                "TransactionAnalysis=" + transactionAnalysis +
                ", Daybook=" + daybook +
                ", CentaData=" + centaData +
                '}';
    }
}
